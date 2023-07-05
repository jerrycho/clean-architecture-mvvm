package com.jerry.clean_architecture_mvvm.di

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME) //indicates that the annotation should be retained at runtime
annotation class TestCaseScope