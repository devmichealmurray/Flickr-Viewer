package com.devmmurray.flickrrocket.data.di

import com.devmmurray.flickrrocket.ui.viewmodel.BaseViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class, RepositoryModule::class, UseCasesModule::class])
interface ViewModelComponent {

    fun inject(baseViewModel: BaseViewModel)
}