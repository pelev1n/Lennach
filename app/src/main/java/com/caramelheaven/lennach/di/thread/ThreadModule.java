package com.caramelheaven.lennach.di.thread;

import com.caramelheaven.lennach.data.datasource.network.LennachApiService;
import com.caramelheaven.lennach.data.repository.thread.ThreadRemoteRepository;
import com.caramelheaven.lennach.domain.ThreadRepository;
import com.caramelheaven.lennach.models.mapper.thread.ThreadMapper;
import com.caramelheaven.lennach.models.mapper.thread.ThreadResponseToPosts;

import dagger.Module;
import dagger.Provides;

@Module
public class ThreadModule {

    @ThreadScope
    @Provides
    ThreadRepository provideThreadRepository(ThreadMapper threadMapper,
                                             LennachApiService apiService) {
        return new ThreadRemoteRepository(apiService, threadMapper);
    }

    @ThreadScope
    @Provides
    ThreadMapper provideThreadMapper(ThreadResponseToPosts threadResponseToPosts) {
        return new ThreadMapper(threadResponseToPosts);
    }

    @ThreadScope
    @Provides
    ThreadResponseToPosts provideThreadResponseToPosts() {
        return new ThreadResponseToPosts();
    }
}
