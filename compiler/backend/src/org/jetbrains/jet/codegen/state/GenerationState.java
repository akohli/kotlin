/*
 * Copyright 2010-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.codegen.state;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jet.codegen.*;
import org.jetbrains.jet.codegen.binding.CodegenBinding;
import org.jetbrains.jet.codegen.intrinsics.IntrinsicMethods;
import org.jetbrains.jet.di.InjectorForJvmCodegen;
import org.jetbrains.jet.lang.descriptors.ScriptDescriptor;
import org.jetbrains.jet.lang.psi.JetClassOrObject;
import org.jetbrains.jet.lang.psi.JetFile;
import org.jetbrains.jet.lang.resolve.BindingContext;
import org.jetbrains.jet.lang.resolve.BindingTrace;
import org.jetbrains.jet.lang.resolve.DelegatingBindingTrace;
import org.jetbrains.jet.util.slicedmap.ReadOnlySlice;
import org.jetbrains.jet.util.slicedmap.WritableSlice;

import java.util.List;

public class GenerationState {
    public interface GenerateClassFilter {
        boolean shouldGenerate(JetClassOrObject classOrObject);

        GenerateClassFilter ONLY_PACKAGE_CLASS = new GenerateClassFilter() {
            @Override
            public boolean shouldGenerate(JetClassOrObject classOrObject) {
                return false;
            }
        };

        GenerateClassFilter GENERATE_ALL = new GenerateClassFilter() {
            @Override
            public boolean shouldGenerate(JetClassOrObject classOrObject) {
                return true;
            }
        };
    }

    private boolean used = false;

    @NotNull
    private final Progress progress;

    @NotNull
    private final List<JetFile> files;

    @NotNull
    private final ClassBuilderMode classBuilderMode;

    @NotNull
    private final BindingContext bindingContext;

    @NotNull
    private final ClassFileFactory classFileFactory;

    @NotNull
    private final Project project;

    @NotNull
    private final IntrinsicMethods intrinsics;

    @NotNull
    private final SamWrapperClasses samWrapperClasses = new SamWrapperClasses(this);

    @NotNull
    private final BindingTrace bindingTrace;

    @NotNull
    private final JetTypeMapper typeMapper;

    private final boolean generateNotNullAssertions;

    private final boolean generateNotNullParamAssertions;

    private final GenerateClassFilter generateDeclaredClasses;

    private final boolean inlineEnabled;

    @Nullable
    private List<ScriptDescriptor> earlierScriptsForReplInterpreter;

    public GenerationState(
            @NotNull Project project,
            @NotNull ClassBuilderFactory builderFactory,
            @NotNull BindingContext bindingContext,
            @NotNull List<JetFile> files,
            boolean inlineEnabled
    ) {
        this(project, builderFactory, Progress.DEAF, bindingContext, files, true, false, GenerateClassFilter.GENERATE_ALL, inlineEnabled);
    }

    public GenerationState(
            @NotNull Project project,
            @NotNull ClassBuilderFactory builderFactory,
            @NotNull Progress progress,
            @NotNull BindingContext bindingContext,
            @NotNull List<JetFile> files,
            boolean generateNotNullAssertions,
            boolean generateNotNullParamAssertions,
            GenerateClassFilter generateDeclaredClasses,
            boolean inlineEnabled
    ) {
        this.project = project;
        this.progress = progress;
        this.files = files;
        this.classBuilderMode = builderFactory.getClassBuilderMode();
        this.inlineEnabled = inlineEnabled;

        bindingTrace = new DelegatingBindingTrace(bindingContext, "trace in GenerationState") {
            @Override
            public <K, V> void record(WritableSlice<K, V> slice, K key, V value) {
                // System.out.println("Record: " + slice + " key " + key + " value " + value);
                super.record(slice, key, value);
            }

            @Override
            public <K> void record(WritableSlice<K, Boolean> slice, K key) {
                // System.out.println("Record: " + slice + " key " + key);
                super.record(slice, key);
            }

            @Override
            public <K, V> V get(ReadOnlySlice<K, V> slice, K key) {
                // System.out.println("Read: " + slice + " key " + key);
                return super.get(slice, key);
            }
        };

        this.bindingContext = bindingTrace.getBindingContext();

        this.typeMapper = new JetTypeMapper(bindingTrace, classBuilderMode);

        InjectorForJvmCodegen injector = new InjectorForJvmCodegen(typeMapper, this, builderFactory, project);

        this.intrinsics = injector.getIntrinsics();
        this.classFileFactory = injector.getClassFileFactory();

        this.generateNotNullAssertions = generateNotNullAssertions;
        this.generateNotNullParamAssertions = generateNotNullParamAssertions;
        this.generateDeclaredClasses = generateDeclaredClasses;
    }

    @NotNull
    public ClassFileFactory getFactory() {
        return classFileFactory;
    }

    @NotNull
    public Progress getProgress() {
        return progress;
    }

    @NotNull
    public BindingContext getBindingContext() {
        return bindingContext;
    }

    @NotNull
    public ClassBuilderMode getClassBuilderMode() {
        return classBuilderMode;
    }

    @NotNull
    public List<JetFile> getFiles() {
        return files;
    }

    @NotNull
    public BindingTrace getBindingTrace() {
        return bindingTrace;
    }

    @NotNull
    public JetTypeMapper getTypeMapper() {
        return typeMapper;
    }

    @NotNull
    public Project getProject() {
        return project;
    }

    @NotNull
    public IntrinsicMethods getIntrinsics() {
        return intrinsics;
    }

    @NotNull
    public SamWrapperClasses getSamWrapperClasses() {
        return samWrapperClasses;
    }

    public boolean isGenerateNotNullAssertions() {
        return generateNotNullAssertions;
    }

    public boolean isGenerateNotNullParamAssertions() {
        return generateNotNullParamAssertions;
    }

    public GenerateClassFilter getGenerateDeclaredClassFilter() {
        return generateDeclaredClasses;
    }

    public boolean isInlineEnabled() {
        return inlineEnabled;
    }

    public void beforeCompile() {
        markUsed();

        //noinspection unchecked
        CodegenBinding.initTrace(getBindingTrace(), getFiles(), getGenerateDeclaredClassFilter());
    }

    private void markUsed() {
        if (used) {
            throw new IllegalStateException(GenerationState.class + " cannot be used more than once");
        }
        used = true;
    }

    public void destroy() {
    }

    @Nullable
    public List<ScriptDescriptor> getEarlierScriptsForReplInterpreter() {
        return earlierScriptsForReplInterpreter;
    }

    public void setEarlierScriptsForReplInterpreter(@Nullable List<ScriptDescriptor> earlierScriptsForReplInterpreter) {
        this.earlierScriptsForReplInterpreter = earlierScriptsForReplInterpreter;
    }
}
