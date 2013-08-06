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

package org.jetbrains.jet.jvm.compiler;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;
import java.util.regex.Pattern;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;

import org.jetbrains.jet.jvm.compiler.AbstractCompileKotlinAgainstKotlinTest;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("compiler/testData/compileKotlinAgainstKotlin")
@InnerTestClasses({CompileKotlinAgainstKotlinTestGenerated.Inline.class})
public class CompileKotlinAgainstKotlinTestGenerated extends AbstractCompileKotlinAgainstKotlinTest {
    public void testAllFilesPresentInCompileKotlinAgainstKotlin() throws Exception {
        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.TestsPackage", new File("compiler/testData/compileKotlinAgainstKotlin"), Pattern.compile("^(.+)\\.A.kt$"), true);
    }
    
    @TestMetadata("ClassInObject.A.kt")
    public void testClassInObject() throws Exception {
        doTest("compiler/testData/compileKotlinAgainstKotlin/ClassInObject.A.kt");
    }
    
    @TestMetadata("ClassObjectMember.A.kt")
    public void testClassObjectMember() throws Exception {
        doTest("compiler/testData/compileKotlinAgainstKotlin/ClassObjectMember.A.kt");
    }
    
    @TestMetadata("ConstructorVararg.A.kt")
    public void testConstructorVararg() throws Exception {
        doTest("compiler/testData/compileKotlinAgainstKotlin/ConstructorVararg.A.kt");
    }
    
    @TestMetadata("DefaultConstructor.A.kt")
    public void testDefaultConstructor() throws Exception {
        doTest("compiler/testData/compileKotlinAgainstKotlin/DefaultConstructor.A.kt");
    }
    
    @TestMetadata("Enum.A.kt")
    public void testEnum() throws Exception {
        doTest("compiler/testData/compileKotlinAgainstKotlin/Enum.A.kt");
    }
    
    @TestMetadata("ImportObject.A.kt")
    public void testImportObject() throws Exception {
        doTest("compiler/testData/compileKotlinAgainstKotlin/ImportObject.A.kt");
    }
    
    @TestMetadata("InnerClass.A.kt")
    public void testInnerClass() throws Exception {
        doTest("compiler/testData/compileKotlinAgainstKotlin/InnerClass.A.kt");
    }
    
    @TestMetadata("InnerClassConstructor.A.kt")
    public void testInnerClassConstructor() throws Exception {
        doTest("compiler/testData/compileKotlinAgainstKotlin/InnerClassConstructor.A.kt");
    }
    
    @TestMetadata("InnerEnum.A.kt")
    public void testInnerEnum() throws Exception {
        doTest("compiler/testData/compileKotlinAgainstKotlin/InnerEnum.A.kt");
    }
    
    @TestMetadata("InnerInnerClass.A.kt")
    public void testInnerInnerClass() throws Exception {
        doTest("compiler/testData/compileKotlinAgainstKotlin/InnerInnerClass.A.kt");
    }
    
    @TestMetadata("InnerObject.A.kt")
    public void testInnerObject() throws Exception {
        doTest("compiler/testData/compileKotlinAgainstKotlin/InnerObject.A.kt");
    }
    
    @TestMetadata("Simple.A.kt")
    public void testSimple() throws Exception {
        doTest("compiler/testData/compileKotlinAgainstKotlin/Simple.A.kt");
    }
    
    @TestMetadata("StarImportEnum.A.kt")
    public void testStarImportEnum() throws Exception {
        doTest("compiler/testData/compileKotlinAgainstKotlin/StarImportEnum.A.kt");
    }
    
    @TestMetadata("compiler/testData/compileKotlinAgainstKotlin/inline")
    public static class Inline extends AbstractCompileKotlinAgainstKotlinTest {
        public void testAllFilesPresentInInline() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("compiler/testData/compileKotlinAgainstKotlin/inline"), Pattern.compile("^(.+)\\.A.kt$"), true);
        }
        
        @TestMetadata("inline.A.kt")
        public void testInline() throws Exception {
            doTest("compiler/testData/compileKotlinAgainstKotlin/inline/inline.A.kt");
        }
        
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("CompileKotlinAgainstKotlinTestGenerated");
        suite.addTestSuite(CompileKotlinAgainstKotlinTestGenerated.class);
        suite.addTestSuite(Inline.class);
        return suite;
    }
}
