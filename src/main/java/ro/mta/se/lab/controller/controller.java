package ro.mta.se.lab.controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.json.JSONException;
import org.json.JSONObject;
import ro.mta.se.lab.model.InformatiiOras;
import ro.mta.se.lab.model.Logger;
import ro.mta.se.lab.model.RaportVreme;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 * Aceasta clasa controleaza si intializeaza toate elemntele din View
 * Functia intializare_lista()-initializeaza lista de <InfromatiiOras> cu date din fisiereul in.txt
 * Functia initializare_tari()-initializeaza lista de tari care este afisata in CHOICEBOX-choice_country
 * Cand selectam o anumita tara se initializeaza si CHOICEBUX-choice_city cu orasele din acea tara
 * Dupa ce am selectat Orasul, se initializeaza un obicet de Tip RaportVreme cu date obtinute dintr-un Json care este descarcat
 * cu functia getinfo(nume_oras).
 */
public class controller {

    private List<InformatiiOras> Orase;
    private RaportVreme raport;
    private ObservableList<String> Lista_Orase;
    private ObservableList<String> Lista_Tari = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox<String> choice_city = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> choice_country = new ChoiceBox<>();
    @FXML
    private Label oras_selectat;
    @FXML
    private Label temperatura;
    @FXML
    private Label data;
    @FXML
    private Label descriere;
    @FXML
    private Label data_ora;
    @FXML
    private Label imagine;
    @FXML
    private Label vreme;
    @FXML
    private Label umiditate;
    @FXML
    private Label vant;
    @FXML
    private void initialize() {


        choice_country.getItems().addAll(Lista_Tari);

        choice_country.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                choice_city.getItems().clear();

                Lista_Orase = FXCollections.observableArrayList();

                String Tara=(String)choice_country.getItems().get((Integer)t1);

                if(Tara!=null) {
                    for (int i = 0; i < Orase.size(); i++) {
                        if (Orase.get(i).getCod().equals(Tara)) {
                            Lista_Orase.add(Orase.get(i).getNume_oras());
                        }
                    }
                    choice_city.getItems().addAll((Lista_Orase));

                }
            }
        });
        choice_city.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                String select_o=null;
                String select_t=null;
                    if((Integer)t1>=0) {
                         select_o = (String) choice_city.getItems().get((Integer) t1);
                         select_t = (String) choice_country.getItems().get((Integer) t1);
                    }
                if(select_o!=null &&select_t!=null)
                {
                    try {
                        initializare_raport(get_inf(select_o));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ImageView img = new ImageView("http://openweathermap.org/img/w/" + raport.getIcon() + ".png");
                    img.setFitHeight(115);
                    img.setFitWidth(100);
                    imagine.setGraphic(img);
                    vreme.setText(raport.getVreme());
                    temperatura.setText(get_celsius_temp(raport.getTemperatura()) + " \u2103");
                    descriere.setText(raport.getDescriere());
                    oras_selectat.setText(select_o);
                    data.setText(raport.getCalendar().getTime().toString());
                    umiditate.setText("Umiditate: "+raport.getUmitiate()+"%");
                    vant.setText("Vant: "+raport.getVant()+"km/h");

                    try {
                        append_log(select_o);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    String append_log(String city) throws IOException {

        Logger log=new Logger();
        String test=log.appendMessage(city);

        return test;

    }

    static String get_celsius_temp(double T)
    {
        double CT=T-272.15;
        return String.format("%.2f",CT);
    }

    private void initializare_raport(JSONObject jsonobj) throws JSONException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, -calendar.getTimeZone().getOffset(calendar.getTimeInMillis()));
        calendar.add(Calendar.SECOND, jsonobj.getInt("timezone"));
        raport=new RaportVreme(
                jsonobj.getJSONObject("main").getDouble("humidity"),
                jsonobj.getJSONObject("wind").getDouble("speed"),
                jsonobj.getJSONObject("main").getDouble("temp"),
                jsonobj.getJSONObject("main").getDouble("temp_max"),
                jsonobj.getJSONObject("main").getDouble("temp_min"),
                jsonobj.getJSONObject("main").getDouble("pressure"),
                jsonobj.getJSONArray("weather").getJSONObject(0).getString("main"),
                jsonobj.getJSONArray("weather").getJSONObject(0).getString("description"),
                jsonobj.getJSONArray("weather").getJSONObject(0).getString("icon"),
                calendar);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, -calendar.getTimeZone().getOffset(calendar.getTimeInMillis()));
        calendar.add(Calendar.SECOND, jsonobj.getInt("timezone"));
    }


    public controller() {
        Orase = new ArrayList<InformatiiOras>();
        intializare_lista();
        initalizare_tari();
        initialize();

    }

    public JSONObject get_inf(String oras) {
        JSONObject jsonObject = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://api.openweathermap.org/data/2.5/weather?q=" + oras + "&appid=add171bbe43a437fafc2de6d00e3d8f9")).build();
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            jsonObject = new JSONObject(response.body().toString());
        } catch (IOException | InterruptedException | JSONException e) {
            return null;
        }
        return jsonObject;
    }

    private int check(String tara)
    {
        for(int i=0;i<Lista_Tari.size();i++)
        {
            if(tara.equals(Lista_Tari.get(i)))
                return 0;
        }
        return 1;
    }
    private void intializare_lista()
    {
        int nr_linie=0;
        int nr_cuv=0;
        int id=0;
        String Denumire_oras="";
        double lat=0;
        double lon=0;
        String CodCountry="";
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(new File("src\\main\\resources\\in.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc2.hasNextLine()) {
            nr_cuv=0;
            Scanner s2 = new Scanner(sc2.nextLine());
            while (s2.hasNext()) {
                String s = s2.next();
                if(nr_linie!=0) {
                    if(nr_cuv==0)
                        id=Integer.parseInt(s);
                    if(nr_cuv==1)
                        Denumire_oras = s;


                    if(nr_cuv==2)
                        lat=Double.parseDouble(s);
                    if(nr_cuv==3)
                        lon = Double.parseDouble(s);
                    if(nr_cuv==4) {
                        CodCountry = s;
                        Orase.add(new InformatiiOras(id, Denumire_oras, lat, lon, CodCountry));
                    }
                    nr_cuv++;

                }

            }
            nr_linie++;
        }

    }
     private void initalizare_tari() {
        for (int i = 0; i < Orase.size(); i++)

        {
            if(check(Orase.get(i).getCod())==1)
            {
                Lista_Tari.add(Orase.get(i).getCod());
            }
        }
    }


}
