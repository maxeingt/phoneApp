package gt.com.xfactory.phoneapp;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import gt.com.xfactory.phoneapp.pojo.Persona;

public class ContactFragment extends Fragment {
View rootView;
RecyclerView listView;
private ContactAdapter pAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*relacionamos la View del XML contact_fragment con este objeto y poder trabajar sobre el*/
        rootView= inflater.inflate(R.layout.contact_fragment, container, false);

        /*relacionamos el RecyclerView que creamos en el persona_fragment.xml para poder utilizarlo*/
        listView = (RecyclerView) rootView.findViewById(R.id.lista_persona);
        listView.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        new ObtenerDataWS().execute();
        return rootView;
    }

    class ObtenerDataWS extends AsyncTask<Void, Void, String> {
        URL url;
        HttpURLConnection urlConnection;
        List<Persona> listaPersonas = new ArrayList<Persona>();

        @Override
        protected String doInBackground(Void... voids) {

                InputStreamReader responseBodyReader = this.urlConnection("http://192.168.0.16:8080/WebServiceTest/webresources/grupo.telus.webservicetest.persona");
                JsonReader jsonReader = new JsonReader(responseBodyReader);

            try {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    listaPersonas.add(readJsonPersona(jsonReader));
                }
                jsonReader.endArray();
            } catch (IOException e) {
                e.printStackTrace();
            }

            pAdapter = new ContactAdapter(listaPersonas, getActivity());
            return null;
        }

        public InputStreamReader urlConnection(String link){
            try {
                url = new URL(link);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                urlConnection.setRequestProperty("Accept", "application/json");
                InputStream responseBody = urlConnection.getInputStream();

                return new InputStreamReader(responseBody, "UTF-8");

            } catch (MalformedURLException e) {
                return null;
            } catch (UnsupportedEncodingException e) {
                return null;
            } catch (ProtocolException e) {
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result){
            listView.setAdapter(pAdapter);
        }

        public Persona readJsonPersona(JsonReader jsonReader) throws IOException{
            Persona persona = new Persona();
            jsonReader.beginObject();

            while(jsonReader.hasNext())
                {
                String name = jsonReader.nextName();
                switch(name){
                    case "nombre": persona.setNombre(jsonReader.nextString()); break;
                    case "edad": persona.setEdad(jsonReader.nextInt()); break;
                    case "telefono": persona.setTelefono(jsonReader.nextString()); break;
                    default: jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
            return persona;
        }
    }
 }
