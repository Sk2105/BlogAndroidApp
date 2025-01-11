package com.composeapp.blogapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composeapp.blogapp.data.converter.ErrorConverterFactory
import com.composeapp.blogapp.data.local.TokenRepository
import com.composeapp.blogapp.data.local.dataStore
import com.composeapp.blogapp.data.remote.AuthServiceApi
import com.composeapp.blogapp.data.remote.AuthorServiceApi
import com.composeapp.blogapp.data.remote.BlogServiceApi
import com.composeapp.blogapp.data.repo.AuthRepoImpl
import com.composeapp.blogapp.data.repo.AuthorRepoImpl
import com.composeapp.blogapp.data.repo.BlogRepoImpl
import com.composeapp.blogapp.domain.repo.AuthRepo
import com.composeapp.blogapp.domain.repo.AuthorRepo
import com.composeapp.blogapp.domain.repo.BlogRepo
import com.composeapp.blogapp.domain.usecases.auth.GetUserDetailsUseCase
import com.composeapp.blogapp.domain.usecases.local.GetUserTokenUseCase
import com.composeapp.blogapp.domain.usecases.auth.LoginUserUseCase
import com.composeapp.blogapp.domain.usecases.auth.RegisterUserUseCase
import com.composeapp.blogapp.domain.usecases.auth.UploadProfileUseCase
import com.composeapp.blogapp.domain.usecases.author.GetAuthorByIdUseCase
import com.composeapp.blogapp.domain.usecases.author.GetAuthorsUseCase
import com.composeapp.blogapp.domain.usecases.author.GetBlogsByAuthorIdUseCase
import com.composeapp.blogapp.domain.usecases.blog.DeleteBlogUseCase
import com.composeapp.blogapp.domain.usecases.blog.GetAllBlogUseCase
import com.composeapp.blogapp.domain.usecases.blog.GetBlogByIdUseCase
import com.composeapp.blogapp.domain.usecases.blog.SaveBlogUseCase
import com.composeapp.blogapp.domain.usecases.local.SaveUserTokenUseCase
import com.composeapp.blogapp.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.map
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    fun provideTokenRepository(dataStore: DataStore<Preferences>): TokenRepository {
        return TokenRepository(dataStore)
    }

    @Provides
    fun provideGetUserTokenUseCase(tokenRepository: TokenRepository): GetUserTokenUseCase {
        return GetUserTokenUseCase(tokenRepository)
    }


    @Provides
    fun provideSaveUserTokenUseCase(tokenRepository: TokenRepository): SaveUserTokenUseCase {
        return SaveUserTokenUseCase(tokenRepository)
    }


    @Provides
    fun provideAuthServiceApi(): AuthServiceApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthServiceApi::class.java)
    }

    @Provides
    fun provideAuthRepo(authServiceApi: AuthServiceApi): AuthRepo {
        return AuthRepoImpl(authServiceApi)
    }

    @Provides
    fun provideGetUserDetailsUseCase(authRepo: AuthRepo): GetUserDetailsUseCase {
        return GetUserDetailsUseCase(authRepo)
    }

    @Provides
    fun provideLoginUserUseCase(authRepo: AuthRepo): LoginUserUseCase {
        return LoginUserUseCase(authRepo)
    }

    @Provides
    fun provideRegisterUserUseCase(authRepo: AuthRepo): RegisterUserUseCase {
        return RegisterUserUseCase(authRepo)
    }

    @Provides
    fun provideBlogServiceApi(): BlogServiceApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BlogServiceApi::class.java)
    }


    @Provides
    fun provideBlogRepo(blogServiceApi: BlogServiceApi): BlogRepo {
        return BlogRepoImpl(blogServiceApi)
    }

    @Provides
    fun provideGetBlogsUseCase(blogRepo: BlogRepo): GetAllBlogUseCase {
        return GetAllBlogUseCase(blogRepo)
    }

    @Provides
    fun provideGetBlogByIdUseCase(blogRepo: BlogRepo): GetBlogByIdUseCase {
        return GetBlogByIdUseCase(blogRepo)
    }

    @Provides
    fun provideSaveBlogUseCase(blogRepo: BlogRepo): SaveBlogUseCase {
        return SaveBlogUseCase(blogRepo)
    }


    @Provides
    fun provideUploadProfileUseCase(authRepo: AuthRepo): UploadProfileUseCase {
        return UploadProfileUseCase(authRepo)
    }


    @Provides
    fun provideAuthorServiceApi(): AuthorServiceApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthorServiceApi::class.java)
    }

    @Provides
    fun provideAuthorRepo(authServiceApi: AuthorServiceApi): AuthorRepo {
        return AuthorRepoImpl(authServiceApi)
    }

    @Provides
    fun provideGetAuthorByIdUseCase(authorRepo: AuthorRepo): GetAuthorByIdUseCase {
        return GetAuthorByIdUseCase(authorRepo)
    }


    @Provides
    fun provideGetAuthorsUseCase(authorRepo: AuthorRepo): GetAuthorsUseCase {
        return GetAuthorsUseCase(authorRepo)
    }

    @Provides
    fun provideGetBlogsByAuthorIdUseCase(authorRepo: AuthorRepo): GetBlogsByAuthorIdUseCase {
        return GetBlogsByAuthorIdUseCase(authorRepo)
    }



    @Provides
    fun provideDeleteBlogUseCase(blogRepo: BlogRepo): DeleteBlogUseCase {
        return DeleteBlogUseCase(blogRepo)
    }
}