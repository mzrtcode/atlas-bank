package com.mzrt.atlas_bank.archtest;


import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(
        packages = "com.mzrt.atlas_bank",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class SecurityIsolationTest {

    @ArchTest
    static final ArchRule domain_should_not_depend_on_spring_security =
            noClasses()
                    .that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("org.springframework.security..")
                    .because("El dominio debe permanecer puro y no depender de frameworks ni mecanismos de autenticación");

    @ArchTest
    static final ArchRule application_should_not_depend_on_spring_security =
            noClasses()
                    .that().resideInAPackage("..application..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("org.springframework.security..")
                    .because("La capa de aplicación debe orquestar casos de uso sin acoplarse a detalles de seguridad o frameworks externos");
}