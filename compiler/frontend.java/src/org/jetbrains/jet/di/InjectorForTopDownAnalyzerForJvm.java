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

package org.jetbrains.jet.di;

import com.intellij.openapi.project.Project;
import org.jetbrains.jet.lang.resolve.TopDownAnalysisParameters;
import org.jetbrains.jet.storage.StorageManager;
import org.jetbrains.jet.lang.resolve.BindingTrace;
import org.jetbrains.jet.lang.descriptors.ModuleDescriptorImpl;
import org.jetbrains.jet.lang.resolve.TopDownAnalyzer;
import org.jetbrains.jet.lang.resolve.TopDownAnalysisContext;
import org.jetbrains.jet.lang.resolve.MutablePackageFragmentProvider;
import org.jetbrains.jet.lang.resolve.java.JavaDescriptorResolver;
import org.jetbrains.jet.lang.resolve.java.mapping.JavaToKotlinClassMap;
import org.jetbrains.jet.lang.resolve.java.JavaClassFinderImpl;
import org.jetbrains.jet.lang.resolve.java.resolver.TraceBasedExternalSignatureResolver;
import org.jetbrains.jet.lang.resolve.java.resolver.TraceBasedJavaResolverCache;
import org.jetbrains.jet.lang.resolve.java.resolver.TraceBasedErrorReporter;
import org.jetbrains.jet.lang.resolve.java.resolver.PsiBasedMethodSignatureChecker;
import org.jetbrains.jet.lang.resolve.java.resolver.PsiBasedExternalAnnotationResolver;
import org.jetbrains.jet.lang.resolve.kotlin.VirtualFileFinder;
import org.jetbrains.jet.lang.resolve.BodyResolver;
import org.jetbrains.jet.lang.resolve.AnnotationResolver;
import org.jetbrains.jet.lang.resolve.calls.CallResolver;
import org.jetbrains.jet.lang.resolve.calls.ArgumentTypeResolver;
import org.jetbrains.jet.lang.types.expressions.ExpressionTypingServices;
import org.jetbrains.jet.lang.resolve.calls.CallExpressionResolver;
import org.jetbrains.jet.lang.resolve.DescriptorResolver;
import org.jetbrains.jet.lang.resolve.DelegatedPropertyResolver;
import org.jetbrains.jet.lang.resolve.TypeResolver;
import org.jetbrains.jet.lang.resolve.QualifiedExpressionResolver;
import org.jetbrains.jet.lang.resolve.calls.CallResolverExtensionProvider;
import org.jetbrains.jet.lang.resolve.calls.CandidateResolver;
import org.jetbrains.jet.lang.resolve.ControlFlowAnalyzer;
import org.jetbrains.jet.lang.resolve.DeclarationsChecker;
import org.jetbrains.jet.lang.resolve.FunctionAnalyzerExtension;
import org.jetbrains.jet.lang.resolve.ScriptBodyResolver;
import org.jetbrains.jet.lang.resolve.DeclarationResolver;
import org.jetbrains.jet.lang.resolve.ImportsResolver;
import org.jetbrains.jet.lang.psi.JetImportsFactory;
import org.jetbrains.jet.lang.resolve.ScriptHeaderResolver;
import org.jetbrains.jet.lang.resolve.OverloadResolver;
import org.jetbrains.jet.lang.resolve.OverrideResolver;
import org.jetbrains.jet.lang.resolve.TypeHierarchyResolver;
import org.jetbrains.jet.lang.resolve.java.lazy.LazyJavaPackageFragmentProvider;
import org.jetbrains.jet.lang.resolve.java.lazy.GlobalJavaResolverContext;
import org.jetbrains.jet.lang.resolve.kotlin.DeserializedDescriptorResolver;
import org.jetbrains.jet.lang.resolve.kotlin.AnnotationDescriptorDeserializer;
import org.jetbrains.annotations.NotNull;
import javax.annotation.PreDestroy;

/* This file is generated by org.jetbrains.jet.generators.injectors.InjectorsPackage. DO NOT EDIT! */
@SuppressWarnings("ALL")
public class InjectorForTopDownAnalyzerForJvm implements InjectorForTopDownAnalyzer {
    
    private final Project project;
    private final TopDownAnalysisParameters topDownAnalysisParameters;
    private final StorageManager storageManager;
    private final BindingTrace bindingTrace;
    private final ModuleDescriptorImpl moduleDescriptor;
    private final TopDownAnalyzer topDownAnalyzer;
    private final TopDownAnalysisContext topDownAnalysisContext;
    private final MutablePackageFragmentProvider mutablePackageFragmentProvider;
    private final JavaDescriptorResolver javaDescriptorResolver;
    private final JavaToKotlinClassMap javaToKotlinClassMap;
    private final JavaClassFinderImpl javaClassFinder;
    private final TraceBasedExternalSignatureResolver traceBasedExternalSignatureResolver;
    private final TraceBasedJavaResolverCache traceBasedJavaResolverCache;
    private final TraceBasedErrorReporter traceBasedErrorReporter;
    private final PsiBasedMethodSignatureChecker psiBasedMethodSignatureChecker;
    private final PsiBasedExternalAnnotationResolver psiBasedExternalAnnotationResolver;
    private final VirtualFileFinder virtualFileFinder;
    private final BodyResolver bodyResolver;
    private final AnnotationResolver annotationResolver;
    private final CallResolver callResolver;
    private final ArgumentTypeResolver argumentTypeResolver;
    private final ExpressionTypingServices expressionTypingServices;
    private final CallExpressionResolver callExpressionResolver;
    private final DescriptorResolver descriptorResolver;
    private final DelegatedPropertyResolver delegatedPropertyResolver;
    private final TypeResolver typeResolver;
    private final QualifiedExpressionResolver qualifiedExpressionResolver;
    private final CallResolverExtensionProvider callResolverExtensionProvider;
    private final CandidateResolver candidateResolver;
    private final ControlFlowAnalyzer controlFlowAnalyzer;
    private final DeclarationsChecker declarationsChecker;
    private final FunctionAnalyzerExtension functionAnalyzerExtension;
    private final ScriptBodyResolver scriptBodyResolver;
    private final DeclarationResolver declarationResolver;
    private final ImportsResolver importsResolver;
    private final JetImportsFactory jetImportsFactory;
    private final ScriptHeaderResolver scriptHeaderResolver;
    private final OverloadResolver overloadResolver;
    private final OverrideResolver overrideResolver;
    private final TypeHierarchyResolver typeHierarchyResolver;
    private final LazyJavaPackageFragmentProvider lazyJavaPackageFragmentProvider;
    private final GlobalJavaResolverContext globalJavaResolverContext;
    private final DeserializedDescriptorResolver deserializedDescriptorResolver;
    private final AnnotationDescriptorDeserializer annotationDescriptorDeserializer;
    
    public InjectorForTopDownAnalyzerForJvm(
        @NotNull Project project,
        @NotNull TopDownAnalysisParameters topDownAnalysisParameters,
        @NotNull BindingTrace bindingTrace,
        @NotNull ModuleDescriptorImpl moduleDescriptor
    ) {
        this.project = project;
        this.topDownAnalysisParameters = topDownAnalysisParameters;
        this.storageManager = topDownAnalysisParameters.getStorageManager();
        this.bindingTrace = bindingTrace;
        this.moduleDescriptor = moduleDescriptor;
        this.topDownAnalyzer = new TopDownAnalyzer();
        this.topDownAnalysisContext = new TopDownAnalysisContext();
        this.mutablePackageFragmentProvider = new MutablePackageFragmentProvider(getModuleDescriptor());
        this.javaClassFinder = new JavaClassFinderImpl();
        this.virtualFileFinder = org.jetbrains.jet.lang.resolve.kotlin.VirtualFileFinder.SERVICE.getInstance(project);
        this.deserializedDescriptorResolver = new DeserializedDescriptorResolver();
        this.psiBasedExternalAnnotationResolver = new PsiBasedExternalAnnotationResolver();
        this.traceBasedExternalSignatureResolver = new TraceBasedExternalSignatureResolver();
        this.traceBasedErrorReporter = new TraceBasedErrorReporter();
        this.psiBasedMethodSignatureChecker = new PsiBasedMethodSignatureChecker();
        this.traceBasedJavaResolverCache = new TraceBasedJavaResolverCache();
        this.globalJavaResolverContext = new GlobalJavaResolverContext(storageManager, javaClassFinder, virtualFileFinder, deserializedDescriptorResolver, psiBasedExternalAnnotationResolver, traceBasedExternalSignatureResolver, traceBasedErrorReporter, psiBasedMethodSignatureChecker, traceBasedJavaResolverCache);
        this.lazyJavaPackageFragmentProvider = new LazyJavaPackageFragmentProvider(globalJavaResolverContext, getModuleDescriptor());
        this.javaDescriptorResolver = new JavaDescriptorResolver(lazyJavaPackageFragmentProvider, getModuleDescriptor());
        this.javaToKotlinClassMap = org.jetbrains.jet.lang.resolve.java.mapping.JavaToKotlinClassMap.getInstance();
        this.bodyResolver = new BodyResolver();
        this.annotationResolver = new AnnotationResolver();
        this.callResolver = new CallResolver();
        this.argumentTypeResolver = new ArgumentTypeResolver();
        this.expressionTypingServices = new ExpressionTypingServices(getTopDownAnalysisContext(), javaToKotlinClassMap);
        this.callExpressionResolver = new CallExpressionResolver();
        this.descriptorResolver = new DescriptorResolver();
        this.delegatedPropertyResolver = new DelegatedPropertyResolver();
        this.typeResolver = new TypeResolver();
        this.qualifiedExpressionResolver = new QualifiedExpressionResolver();
        this.callResolverExtensionProvider = new CallResolverExtensionProvider();
        this.candidateResolver = new CandidateResolver();
        this.controlFlowAnalyzer = new ControlFlowAnalyzer();
        this.declarationsChecker = new DeclarationsChecker();
        this.functionAnalyzerExtension = new FunctionAnalyzerExtension();
        this.scriptBodyResolver = new ScriptBodyResolver();
        this.declarationResolver = new DeclarationResolver();
        this.importsResolver = new ImportsResolver();
        this.jetImportsFactory = new JetImportsFactory();
        this.scriptHeaderResolver = new ScriptHeaderResolver();
        this.overloadResolver = new OverloadResolver();
        this.overrideResolver = new OverrideResolver();
        this.typeHierarchyResolver = new TypeHierarchyResolver();
        this.annotationDescriptorDeserializer = new AnnotationDescriptorDeserializer(storageManager);

        this.topDownAnalyzer.setBodyResolver(bodyResolver);
        this.topDownAnalyzer.setContext(topDownAnalysisContext);
        this.topDownAnalyzer.setDeclarationResolver(declarationResolver);
        this.topDownAnalyzer.setModuleDescriptor(moduleDescriptor);
        this.topDownAnalyzer.setOverloadResolver(overloadResolver);
        this.topDownAnalyzer.setOverrideResolver(overrideResolver);
        this.topDownAnalyzer.setPackageFragmentProvider(mutablePackageFragmentProvider);
        this.topDownAnalyzer.setTopDownAnalysisParameters(topDownAnalysisParameters);
        this.topDownAnalyzer.setTrace(bindingTrace);
        this.topDownAnalyzer.setTypeHierarchyResolver(typeHierarchyResolver);

        this.topDownAnalysisContext.setTopDownAnalysisParameters(topDownAnalysisParameters);

        javaClassFinder.setProject(project);

        traceBasedExternalSignatureResolver.setExternalAnnotationResolver(psiBasedExternalAnnotationResolver);
        traceBasedExternalSignatureResolver.setTrace(bindingTrace);

        traceBasedJavaResolverCache.setTrace(bindingTrace);

        traceBasedErrorReporter.setTrace(bindingTrace);

        psiBasedMethodSignatureChecker.setExternalAnnotationResolver(psiBasedExternalAnnotationResolver);
        psiBasedMethodSignatureChecker.setExternalSignatureResolver(traceBasedExternalSignatureResolver);

        bodyResolver.setAnnotationResolver(annotationResolver);
        bodyResolver.setCallResolver(callResolver);
        bodyResolver.setContext(topDownAnalysisContext);
        bodyResolver.setControlFlowAnalyzer(controlFlowAnalyzer);
        bodyResolver.setDeclarationsChecker(declarationsChecker);
        bodyResolver.setDelegatedPropertyResolver(delegatedPropertyResolver);
        bodyResolver.setExpressionTypingServices(expressionTypingServices);
        bodyResolver.setFunctionAnalyzerExtension(functionAnalyzerExtension);
        bodyResolver.setScriptBodyResolverResolver(scriptBodyResolver);
        bodyResolver.setTopDownAnalysisParameters(topDownAnalysisParameters);
        bodyResolver.setTrace(bindingTrace);

        annotationResolver.setCallResolver(callResolver);
        annotationResolver.setExpressionTypingServices(expressionTypingServices);

        callResolver.setArgumentTypeResolver(argumentTypeResolver);
        callResolver.setCandidateResolver(candidateResolver);
        callResolver.setExpressionTypingServices(expressionTypingServices);
        callResolver.setTypeResolver(typeResolver);

        argumentTypeResolver.setExpressionTypingServices(expressionTypingServices);
        argumentTypeResolver.setTypeResolver(typeResolver);

        expressionTypingServices.setAnnotationResolver(annotationResolver);
        expressionTypingServices.setCallExpressionResolver(callExpressionResolver);
        expressionTypingServices.setCallResolver(callResolver);
        expressionTypingServices.setDescriptorResolver(descriptorResolver);
        expressionTypingServices.setExtensionProvider(callResolverExtensionProvider);
        expressionTypingServices.setProject(project);
        expressionTypingServices.setTypeResolver(typeResolver);

        callExpressionResolver.setExpressionTypingServices(expressionTypingServices);

        descriptorResolver.setAnnotationResolver(annotationResolver);
        descriptorResolver.setDelegatedPropertyResolver(delegatedPropertyResolver);
        descriptorResolver.setExpressionTypingServices(expressionTypingServices);
        descriptorResolver.setStorageManager(storageManager);
        descriptorResolver.setTypeResolver(typeResolver);

        delegatedPropertyResolver.setExpressionTypingServices(expressionTypingServices);

        typeResolver.setAnnotationResolver(annotationResolver);
        typeResolver.setModuleDescriptor(moduleDescriptor);
        typeResolver.setQualifiedExpressionResolver(qualifiedExpressionResolver);

        candidateResolver.setArgumentTypeResolver(argumentTypeResolver);

        controlFlowAnalyzer.setTopDownAnalysisParameters(topDownAnalysisParameters);
        controlFlowAnalyzer.setTrace(bindingTrace);

        declarationsChecker.setTrace(bindingTrace);

        functionAnalyzerExtension.setTrace(bindingTrace);

        scriptBodyResolver.setContext(topDownAnalysisContext);
        scriptBodyResolver.setExpressionTypingServices(expressionTypingServices);
        scriptBodyResolver.setTrace(bindingTrace);

        declarationResolver.setAnnotationResolver(annotationResolver);
        declarationResolver.setContext(topDownAnalysisContext);
        declarationResolver.setDescriptorResolver(descriptorResolver);
        declarationResolver.setImportsResolver(importsResolver);
        declarationResolver.setScriptHeaderResolver(scriptHeaderResolver);
        declarationResolver.setTrace(bindingTrace);

        importsResolver.setContext(topDownAnalysisContext);
        importsResolver.setImportsFactory(jetImportsFactory);
        importsResolver.setModuleDescriptor(moduleDescriptor);
        importsResolver.setQualifiedExpressionResolver(qualifiedExpressionResolver);
        importsResolver.setTrace(bindingTrace);

        jetImportsFactory.setProject(project);

        scriptHeaderResolver.setContext(topDownAnalysisContext);
        scriptHeaderResolver.setDependencyClassByQualifiedNameResolver(javaDescriptorResolver);
        scriptHeaderResolver.setPackageFragmentProvider(mutablePackageFragmentProvider);
        scriptHeaderResolver.setTopDownAnalysisParameters(topDownAnalysisParameters);
        scriptHeaderResolver.setTrace(bindingTrace);

        overloadResolver.setContext(topDownAnalysisContext);
        overloadResolver.setTrace(bindingTrace);

        overrideResolver.setContext(topDownAnalysisContext);
        overrideResolver.setTopDownAnalysisParameters(topDownAnalysisParameters);
        overrideResolver.setTrace(bindingTrace);

        typeHierarchyResolver.setContext(topDownAnalysisContext);
        typeHierarchyResolver.setDescriptorResolver(descriptorResolver);
        typeHierarchyResolver.setImportsResolver(importsResolver);
        typeHierarchyResolver.setPackageFragmentProvider(mutablePackageFragmentProvider);
        typeHierarchyResolver.setScriptHeaderResolver(scriptHeaderResolver);
        typeHierarchyResolver.setTrace(bindingTrace);

        deserializedDescriptorResolver.setAnnotationDeserializer(annotationDescriptorDeserializer);
        deserializedDescriptorResolver.setErrorReporter(traceBasedErrorReporter);
        deserializedDescriptorResolver.setJavaDescriptorResolver(javaDescriptorResolver);
        deserializedDescriptorResolver.setJavaPackageFragmentProvider(lazyJavaPackageFragmentProvider);
        deserializedDescriptorResolver.setStorageManager(storageManager);

        annotationDescriptorDeserializer.setErrorReporter(traceBasedErrorReporter);
        annotationDescriptorDeserializer.setJavaDescriptorResolver(javaDescriptorResolver);
        annotationDescriptorDeserializer.setKotlinClassFinder(virtualFileFinder);

        javaClassFinder.initialize();

    }
    
    @PreDestroy
    public void destroy() {
    }
    
    public ModuleDescriptorImpl getModuleDescriptor() {
        return this.moduleDescriptor;
    }
    
    public TopDownAnalyzer getTopDownAnalyzer() {
        return this.topDownAnalyzer;
    }
    
    public TopDownAnalysisContext getTopDownAnalysisContext() {
        return this.topDownAnalysisContext;
    }
    
    public JavaDescriptorResolver getJavaDescriptorResolver() {
        return this.javaDescriptorResolver;
    }
    
}
