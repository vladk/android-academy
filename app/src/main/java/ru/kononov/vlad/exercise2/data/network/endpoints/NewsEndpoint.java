package ru.kononov.vlad.exercise2.data.network.endpoints;


import androidx.annotation.NonNull;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.kononov.vlad.exercise2.data.network.dto.NewsResponseDTO;

public interface NewsEndpoint {
    @NonNull
    @GET("/svc/topstories/v2/{category}.json")
    Observable<NewsResponseDTO> topStories(@Path("category") String category);
}
