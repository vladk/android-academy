package ru.kononov.vlad.exercise2.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.kononov.vlad.exercise2.data.network.dto.MultimediaDTO;
import ru.kononov.vlad.exercise2.data.network.dto.NewsItemDTO;

public class NewsItemConverter {
    public static NewsItem fromNetwork(NewsItemDTO item) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        String url = null;
        if (item.multimedia != null && !item.multimedia.isEmpty()) {
            MultimediaDTO media = item.multimedia.get(0);
            if (media != null) {
                url = media.url;
            }
        }

        if (url == null) {
            url = "https://via.placeholder.com/200x200";
        }
        return new NewsItem(item.title, url, item.subsection, format.parse(item.publishedDate), item.theAbstract, item.url);
    }
}