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

import com.google.common.base.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jet.lang.psi.JetFile;
import org.jetbrains.jet.lang.resolve.java.JavaClassFinder;
import org.jetbrains.jet.lang.resolve.lazy.declarations.FileBasedDeclarationProviderFactory;
import org.jetbrains.jet.lang.resolve.name.FqName;
import org.jetbrains.jet.storage.StorageManager;

import java.util.ArrayList;
import java.util.Collection;

public class FileBasedDeclarationConfigurationWithJava extends FileBasedDeclarationProviderFactory {
    public FileBasedDeclarationConfigurationWithJava(
            @NotNull StorageManager storageManager,
            @NotNull final JavaClassFinder classFinder
            // @NotNull final FileBaseDeclarationConfiguration configuration
    ) {
        super(storageManager, /* new FileBaseDeclarationConfiguration() {
            @NotNull
            @Override
            public Collection<JetFile> getFiles() {
                return null; // configuration.getFiles();
            }

            @NotNull
            @Override
            public Predicate<FqName> isPackageDeclaredExternallyPredicate() {
                return new Predicate<FqName>() {
                    @Override
                    public boolean apply(FqName fqName) {
                        return true; // classFinder.findPackage(fqName) != null ||
                               // configuration.isPackageDeclaredExternallyPredicate().apply(fqName);
                    }
                };
            }
        } */ new ArrayList<JetFile>());
    }
}
