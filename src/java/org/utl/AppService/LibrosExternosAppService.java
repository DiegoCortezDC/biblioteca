package org.utl.AppService;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Arrays;
import org.utl.viewmodel.LibroViewModels;

public class LibrosExternosAppService {
    
   private static final List<String> EXTERNAL_API_URLS = Arrays.asList(
        "http://10.16.1.74:8080/SistemaGestionArq/api/libro/getAllLibrosPublic", // Sergio
        "http://10.16.25.67:8080/PracticaLogin/api/producto/getLibrosPublic", // Aroon
        "http://10.16.18.48:8080/UniversidadIbero/api/libro/getAllLibrosPublico", // Hector
        "http://10.16.20.101:3000/api/book/publicBooks" //alexis
    );

    public List<LibroViewModels> getAllLibrosExternos() {
        List<LibroViewModels> allExternalBooks = new ArrayList<>();
        
        for (String apiUrl : EXTERNAL_API_URLS) {
            List<LibroViewModels> booksFromApi = fetchBooksFromApi(apiUrl);
            allExternalBooks.addAll(booksFromApi);
        }
        
        return allExternalBooks;
    }

    private List<LibroViewModels> fetchBooksFromApi(String apiUrl) {
        List<LibroViewModels> externalBooks = new ArrayList<>();
        
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();                
                Gson gson = new Gson();
                List<LibroViewModels> libros = gson.fromJson(response.toString(), new TypeToken<List<LibroViewModels>>(){}.getType());
                externalBooks.addAll(libros);
            } else {
                System.out.println("GET request failed for URL: " + apiUrl + " with response code: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return externalBooks;
    }
}