package meuaplicativo.cursoandroid.com.conversor4a20;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    Spinner tiposUnidades;
    Spinner tiposUnidades2;
    String itemSelecionado;

    private EditText limiteInferior;
    private EditText limiteSuperior;
    private EditText porcentagem;
    private EditText unidade;
    private EditText ma;
    private EditText unidade2;
    private EditText entrada;
    private EditText saida;
    private Button btnCalcular;
    private Button btnLimpar;
    private TextView btnSobre;
    private TextView resultadoPorcentagem;
    private TextView resultadoUnidade;
    private TextView resultadoMA;
    private TextView resultadoUnidade2;
    private TextView resultadoEntrada;
    private TextView resultadoSaida;
    private TextView textUnidade1;
    private TextView textUnidade2;
    private TextView textUnidade3;
    private TextView textUnidade4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Localizando elementos na tela
        limiteInferior = (EditText) findViewById(R.id.limInferiorId);
        limiteSuperior = (EditText) findViewById(R.id.limSuperiorId);
        porcentagem = (EditText) findViewById(R.id.editTextPorcentagemId);
        unidade = (EditText) findViewById(R.id.editTextUnidadeId);
        ma = (EditText) findViewById(R.id.editTextMA);
        unidade2 = (EditText) findViewById(R.id.editTextUnidade2Id);
        entrada = (EditText) findViewById(R.id.editTextEntradaId);
        saida = (EditText) findViewById(R.id.editTextSaidaId);
        btnCalcular = (Button) findViewById(R.id.btnCalcularId);
        btnLimpar = (Button) findViewById(R.id.btnLimparId);
        btnSobre = (TextView) findViewById(R.id.btnSobreId);

        resultadoPorcentagem = (TextView) findViewById(R.id.resultPorcentagemId);
        resultadoUnidade = (TextView) findViewById(R.id.resultUnidadeId);
        resultadoMA = (TextView) findViewById(R.id.resultMAId);
        resultadoUnidade2 = (TextView) findViewById(R.id.resultUnidade2Id);
        resultadoEntrada = (TextView) findViewById(R.id.resultEntradaId);
        resultadoSaida = (TextView) findViewById(R.id.resultSaidaId);

        textUnidade1 = (TextView) findViewById(R.id.textUnidade1);
        textUnidade2 = (TextView) findViewById(R.id.textUnidade2);
        textUnidade3 = (TextView) findViewById(R.id.textUnidade3);
        textUnidade4 = (TextView) findViewById(R.id.textUnidade4);

        // Adiciona listener para o botão "sobre"
        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, btnSobre);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        switch (id){
                            case R.id.itemEmail:
                                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("label", "thiagolubiana@gmail.com");
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(getApplicationContext(), "Copiado para área de transferência.", Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        // Instanciando Spinner 1
        tiposUnidades = (Spinner) findViewById(R.id.spinTiposUnidades);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.tipos_unidades, R.layout.spinner_layout_unidades);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_unidades);
        tiposUnidades.setAdapter(adapter);
        // Instanciando Spinner 2
        tiposUnidades2 = (Spinner) findViewById(R.id.spinTiposUnidades2);
        tiposUnidades2.setAdapter(adapter);

        // Listener para o Spinner1
        tiposUnidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textUnidade1.setText(tiposUnidades.getSelectedItem().toString());
                textUnidade2.setText(tiposUnidades.getSelectedItem().toString());
                textUnidade3.setText(tiposUnidades.getSelectedItem().toString());
                textUnidade4.setText(tiposUnidades.getSelectedItem().toString());
                int posicaoSelecionada = tiposUnidades.getSelectedItemPosition();
                tiposUnidades2.setSelection(posicaoSelecionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Listener para o Spinner2
        tiposUnidades2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textUnidade1.setText(tiposUnidades.getSelectedItem().toString());
                textUnidade2.setText(tiposUnidades.getSelectedItem().toString());
                textUnidade3.setText(tiposUnidades.getSelectedItem().toString());
                textUnidade4.setText(tiposUnidades.getSelectedItem().toString());
                int posicaoSelecionada = tiposUnidades2.getSelectedItemPosition();
                tiposUnidades.setSelection(posicaoSelecionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Escondendo o teclado após clicar no botão
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(limiteInferior.getWindowToken(),0);

                //Recuperando valores digitados
                String textoLimiteInferior = limiteInferior.getText().toString();
                String textoLimiteSuperior = limiteSuperior.getText().toString();
                String textoPorcentagem = porcentagem.getText().toString();
                String textoUnidade = unidade.getText().toString();
                String textoMa = ma.getText().toString();
                String textoUnidade2 = unidade2.getText().toString();
                String textoEntrada = entrada.getText().toString();
                String textoSaida = saida.getText().toString();

                // Verificar os campos digitados
                Double valorLimiteInferior = null;
                if (textoLimiteInferior.isEmpty()) {
                } else {
                    valorLimiteInferior = Double.parseDouble(textoLimiteInferior);
                }
                Double valorLimiteSuperior = null;
                if (textoLimiteSuperior.isEmpty()) {
                } else {
                    valorLimiteSuperior = Double.parseDouble(textoLimiteSuperior);
                }
                Double valorPorcentagem = null;
                if (textoPorcentagem.isEmpty()) {
                } else {
                    valorPorcentagem = Double.parseDouble(textoPorcentagem);
                }
                Double valorUnidade = null;
                if (textoUnidade.isEmpty()) {
                } else {
                    valorUnidade = Double.parseDouble(textoUnidade);
                }
                Double valorMa = null;
                if (textoMa.isEmpty()) {
                } else {
                    valorMa = Double.parseDouble(textoMa);
                }
                Double valorUnidade2 = null;
                if (textoUnidade2.isEmpty()) {
                } else {
                    valorUnidade2 = Double.parseDouble(textoUnidade2);
                }
                Double valorEntrada = null;
                if (textoEntrada.isEmpty()) {
                } else {
                    valorEntrada = Double.parseDouble(textoEntrada);
                }
                Double valorSaida = null;
                if (textoSaida.isEmpty()) {
                } else {
                    valorSaida = Double.parseDouble(textoSaida);
                }

                //Cálculos
                Float valor;
                Float flagCalculou = 0f;
                // Verifica se os campos necessários para cada cálculo estão vazios.
                if (valorPorcentagem != null && valorLimiteSuperior!= null && valorLimiteInferior!= null) {
                    Double rPorcentagem = -((100 - valorPorcentagem) * (valorLimiteSuperior - valorLimiteInferior) - (100 * valorLimiteSuperior)) / 100;
                    valor = new BigDecimal(rPorcentagem).setScale(2, RoundingMode.HALF_UP).floatValue();
                    resultadoPorcentagem.setText(valor.toString());
                    flagCalculou ++;
                }
                if (valorLimiteSuperior != null && valorUnidade != null && valorLimiteInferior != null) {
                    Double rUnidade = -(100 * (valorLimiteSuperior - valorUnidade) - ((valorLimiteSuperior - valorLimiteInferior) * 100)) / (valorLimiteSuperior - valorLimiteInferior);
                    valor = new BigDecimal(rUnidade).setScale(2, RoundingMode.HALF_UP).floatValue();
                    resultadoUnidade.setText(valor.toString());
                    flagCalculou ++;
                }
                if (valorLimiteSuperior != null && valorMa != null && valorLimiteInferior != null) {
                    Double rMa = -(((valorLimiteSuperior - valorLimiteInferior) * (20 - valorMa)) - (valorLimiteSuperior * (16))) / (16);
                    valor = new BigDecimal(rMa).setScale(2, RoundingMode.HALF_UP).floatValue();
                    resultadoMA.setText(valor.toString());
                    flagCalculou ++;
                }
                if (valorLimiteSuperior != null && valorUnidade2 != null && valorLimiteInferior != null) {
                    Double rUnidade2 = -((valorLimiteSuperior - valorUnidade2) * (16) - (20 * (valorLimiteSuperior - valorLimiteInferior))) / (valorLimiteSuperior - valorLimiteInferior);
                    valor = new BigDecimal(rUnidade2).setScale(2, RoundingMode.HALF_UP).floatValue();
                    resultadoUnidade2.setText(valor.toString());
                    flagCalculou ++;
                }
                if (valorEntrada != null) {
                    Double rEntrada = (Math.pow(valorEntrada, 0.5)) * 10;
                    valor = new BigDecimal(rEntrada).setScale(2, RoundingMode.HALF_UP).floatValue();
                    resultadoEntrada.setText(valor.toString());
                    flagCalculou ++;
                }
                if (valorSaida != null) {
                    Double rSaida = Math.pow((valorSaida / 10), 2);
                    valor = new BigDecimal(rSaida).setScale(2, RoundingMode.HALF_UP).floatValue();
                    resultadoSaida.setText(valor.toString());
                    flagCalculou ++;
                }

                if (flagCalculou == 0f) {
                    Toast.makeText(getApplicationContext(), "Favor preencher todos os campos necessários.", Toast.LENGTH_SHORT).show();
                }
            }

        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                limiteInferior.setText("");
                limiteSuperior.setText("");
                porcentagem.setText("");
                unidade.setText("");
                ma.setText("");
                unidade2.setText("");
                entrada.setText("");
                saida.setText("");
                resultadoPorcentagem.setText("");
                resultadoUnidade.setText("");
                resultadoMA.setText("");
                resultadoUnidade2.setText("");
                resultadoEntrada.setText("");
                resultadoSaida.setText("");
            }

        });
    }
}