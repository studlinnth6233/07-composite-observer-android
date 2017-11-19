package de.fhro.inf.prg3.a07.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import de.fhro.inf.prg3.a07.api.OpenMensaAPI;
import de.fhro.inf.prg3.a07.model.Meal;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by Peter Kurfer on 11/19/17.
 */

public class OpenMensaAPITests {

    private static final Logger logger = Logger.getLogger(OpenMensaAPITests.class.getName());
    private OpenMensaAPI openMensaAPI;

    @BeforeEach
    public void setup() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://openmensa.org/api/v2/")
                .build();

        openMensaAPI = retrofit.create(OpenMensaAPI.class);
    }

    @Test
    public void testGetMeals() throws IOException {
        //TODO prepare call

        //TODO execute the call synchronously

        //TODO unwrap the body
        List<Meal> meals = null;

        assertNotNull(meals);
        assertNotEquals(0, meals.size());

        for(Meal m : meals){
            logger.info(m.toString());
        }
    }

}
