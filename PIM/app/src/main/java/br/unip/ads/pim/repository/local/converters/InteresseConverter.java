package br.unip.ads.pim.repository.local.converters;

import androidx.room.TypeConverter;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.unip.ads.pim.model.interesses.Interesse;
import br.unip.ads.pim.repository.remote.RemoteDataSingleton;

public class InteresseConverter {

    @TypeConverter
    public List<Interesse> fromString(String json) {
        Type listType = new TypeToken<ArrayList<Interesse>>() { }.getType();
        return RemoteDataSingleton.get().gson.fromJson(json, listType);
    }

    @TypeConverter
    public String fromList(List<Interesse> list) {
        return RemoteDataSingleton.get().gson.toJson(list);
    }
}
