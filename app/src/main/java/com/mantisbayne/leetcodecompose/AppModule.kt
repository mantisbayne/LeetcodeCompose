package com.mantisbayne.leetcodecompose

import android.content.Context
import com.mantisbayne.leetcodecompose.data.SolutionsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideSolutionsService(@ApplicationContext context: Context): SolutionsService =
        SolutionsService(context)
}
