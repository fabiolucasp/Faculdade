package com.fabiolucas.faculdade.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fabiolucas.faculdade.R;
import com.fabiolucas.faculdade.infos.Pessoa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button loginButton = view.findViewById(R.id.btnAdicionar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView txtNome, txtEmail, txtTelefone, txtSenha, txtConfirmaSenha;

                EditText edtNome, edtEmail, edtTelefone, edtSenha, edtConfirmaSenha;

                edtNome = view.findViewById(R.id.edtNome);
                edtEmail = view.findViewById(R.id.edtEmail);
                edtTelefone = view.findViewById(R.id.edtTelefone);
                edtSenha = view.findViewById(R.id.edtSenha);
                edtConfirmaSenha = view.findViewById(R.id.edtConfirmaSenha);

                String nome, email, telefone, senha, confirmasenha;

                nome = edtNome.getText().toString();
                email = edtEmail.getText().toString();
                telefone = edtTelefone.getText().toString();
                senha = edtSenha.getText().toString();
                confirmasenha = edtConfirmaSenha.getText().toString();

                Pessoa pessoa = new Pessoa(nome, email, telefone, senha);

                List<Pessoa> lista = new ArrayList<>();

                lista.add(pessoa);

                ListView listaDados;

                HashMap<String, String> map = new HashMap<>();

                List<String> strings = new ArrayList<>();

                List<HashMap<String, String>> hashMaps = new ArrayList<>();

                for (int c = 0; c < lista.size(); c++) {

                    map.put("nome", pessoa.getNome());
                    map.put("email", pessoa.getEmail());
                    map.put("telefone", pessoa.getTelefone());
                    map.put("senha", pessoa.getSenha());

                    hashMaps.add(map);
                }

                String[] de = {"nome", "email", "telefone"};
                int[] para = {R.id.txtNome, R.id.txtEmail, R.id.txtTelefone};

                listaDados = view.findViewById(R.id.listaDados);

                //SIMPLE ADAPTER
                SimpleAdapter adapter =
                        new SimpleAdapter(getContext(), hashMaps,
                                R.layout.exibir, de, para);


                listaDados.setAdapter(adapter);


                Toast.makeText(getContext(), "DEU CERTO", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        String url = "https://swapi.co/api/people/";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() > 0) {

                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");

                        ListView listarDadosJSON;

                        List<String> listaBirthYear = new ArrayList<>();
                        List<String> listaEyeColor = new ArrayList<>();
                        List<String> listaGender = new ArrayList<>();
                        List<String> listaHairColor = new ArrayList<>();

                        for (int c = 0; c < jsonArray.length(); c++) {
                            try {

                                jsonObject = jsonArray.getJSONObject(c);
                                String BirthYear = jsonObject.getString("birth_year");
                                String EyeColor = jsonObject.getString("eye_color");
                                String Gender = jsonObject.getString("gender");
                                String HairColor = jsonObject.getString("hair_color");

                                listaBirthYear.add(BirthYear);
                                listaEyeColor.add(EyeColor);
                                listaGender.add(Gender);
                                listaHairColor.add(HairColor);

                            }catch (JSONException jsonException2) {

                                jsonException2.printStackTrace();

                            }

                            List<HashMap<String, String>> hashMaps = new ArrayList<>();

                            HashMap<String, String> map = new HashMap<>();

                            for (c = 0; c < jsonArray.length(); c++) {

                                map.put("anonascimento", listaBirthYear.toString());
                                map.put("genero", listaEyeColor.toString());
                                map.put("corcabelo", listaGender.toString());
                                map.put("corolhos", listaHairColor.toString());

                                hashMaps.add(map);
                            }

                            String[] de = {"anonascimento", "genero", "corcabelo", "corolhos"};
                            int[] para = {R.id.txtAnoNascimento, R.id.txtGenero, R.id.txtCorCabelo, R.id.txtCorOlhos};

                            listarDadosJSON = view.findViewById(R.id.listaDados);

                            //SIMPLE ADAPTER
                            SimpleAdapter adapter =
                                    new SimpleAdapter(getContext(),hashMaps,
                                            R.layout.json,de,para);


                            listarDadosJSON.setAdapter(adapter);


                        }

                    } catch (JSONException jsonException) {

                        jsonException.printStackTrace();

                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

            }

        });

        requestQueue.add(stringRequest);



    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


}
