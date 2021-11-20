package pt.ubi.di.pmd.a44149_t9;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Jogo extends MainActivity {
    public static final String LOGCATTAG = "DEMO";
    public int numRondas;
    public int numJogadores;
    public int maxChars;
    public int maxPalavras;
    public boolean jogadorAleatorio;
    public ArrayList<Integer> arrayJogadores;
    public int qtdJogadores;
    public int rondaAtual;

    TextView playerX;
    TextView roundXY;
    TextView words;
    TextView currText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jogo);
        /*Vai receber os valores das options vindo do main*/
        Intent i = getIntent();
        numJogadores = Integer.parseInt(i.getStringExtra("numJog"));
        numRondas = Integer.parseInt(i.getStringExtra("numRond"));
        maxChars = Integer.parseInt(i.getStringExtra("maxChars"));
        maxPalavras = Integer.parseInt(i.getStringExtra("maxPal"));
        jogadorAleatorio = i.getBooleanExtra("jogadorAle",false);

        startGame();

    }

    /**
     * Voltar para trás, para o mainActivity
     * @param v
     */
    public void goBack(View v)
    {
        super.finish();
        overridePendingTransition(0, 0);
    }

    /**
     * Função para continuar a jogar, ao mesmo tempo muda de jogador, altera as textViews e muda a ronda caso seja preciso
     * No caso de ser a ultima jogada vai mudar para a função finalgame()
     * @param v
     */
    public void goNext(View v)
    {
        //Verificar se o editText tem pelo menos maxPalavras!!
        String s = currText.getText().toString();
        Snackbar mySnackbar = Snackbar.make(v, "Tem que ter pelo menos "+String.valueOf(maxPalavras)+" palavras!", 2000);
        if(countWordsUsingSplit(s) < maxPalavras)
        {
            mySnackbar.show();
        }
        else
        {
            writeToFile(s+" ",getApplicationContext(),1);

            //depois de ter as palavras necessarias então muda de jogador

                qtdJogadores += 1;
                if (qtdJogadores > numJogadores)
                {
                    qtdJogadores = 1;
                    rondaAtual +=1;

                    if(jogadorAleatorio)
                    {
                        Collections.shuffle(arrayJogadores);
                    }
                }
                if(rondaAtual > numRondas)
                {
                    //Mudar para a atividade do jogo final
                    finalGame();

                }else{
                    //Resetar o jogo para proxima ronda/jogador
                    resetGame();
                }

        }
        //Atualiza os jogadores

        return;
    }

    /**
     * função para contar palavras
     * @param input
     * @return
     */
    public static int countWordsUsingSplit(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        String[] words = input.split("\\s+");
        return words.length;
    }

    /**
     * Escrever do editText para file
     * @param data
     * @param context
     * @param mode
     */
    private void writeToFile(String data, Context context, int mode) {

        try {
            // MODE_APPEND, MODE_WORLD_READABLE, MODE_WORLD_WRITEABLE
            // create new file or rewrite existing
            FileOutputStream fos;
            //mode 0 == create e mode 1 == append
            if(mode == 0) {
                fos = openFileOutput("savedData.txt", context.MODE_PRIVATE);
            }else {
                fos = openFileOutput("savedData.txt", context.MODE_APPEND);
                }

            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e("writeTo", "Can not read file: " + e.toString());
        }
    }

    /**
     * ler do file para textview
     * @param context
     * @return
     */
    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("savedData.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("readFrom activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("readFrom activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

    /**
     * Função usada para iniciar o jogo a primeira vez
     */
    public void startGame()
    {
        /*Vai iniciar as textview para depois serem mudadas durante o jogo*/
        playerX = findViewById(R.id.jogadorTVId);
        roundXY = findViewById(R.id.rondaTVId);
        words = findViewById(R.id.palavrasTVId);
        currText = findViewById(R.id.textoETId);
        currText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxChars)});

        /*resetar o ficheiro*/
        writeToFile("", getApplicationContext(),0);


        /*Criação da lista de jogadores e shuffle na mesma caso random option esteja ativa*/
        arrayJogadores = new ArrayList<Integer>();
        for(int u = 1; u <= numJogadores;u++)
            arrayJogadores.add(u);
        if(jogadorAleatorio)
        {
            Collections.shuffle(arrayJogadores);
        }

        qtdJogadores = 1;
        rondaAtual = 1;
        playerX.setText("Player"+String.valueOf(arrayJogadores.get(0)));
        roundXY.setText("Round"+String.valueOf(String.valueOf(rondaAtual)+"/"+String.valueOf(numRondas)));
        words.setText("");
        currText.setText("");
    }

    /**
     * Apaga todos os valores
     */
    public void resetGame()
    {
        //Resetar o jogo para proxima ronda/jogador
        playerX.setText("Player"+String.valueOf(arrayJogadores.get(qtdJogadores-1)));
        roundXY.setText("Round"+String.valueOf(String.valueOf(rondaAtual)+"/"+String.valueOf(numRondas)));
        words.setText("");
        currText.setText("");

        //Parte da função que vai buscar as N palavras a mostrar
        String newWords = "";
        String savedPalavras = readFromFile(getApplicationContext());

        String[] arraySavedPalavras = savedPalavras.split(" ");
        for(int o = (arraySavedPalavras.length - maxPalavras); o < arraySavedPalavras.length;o++)
        {
            newWords = (new StringBuilder()).append(newWords).append(" ").append(arraySavedPalavras[o]).toString();
        }
        words.setText(newWords);
    }

    /**
     * Inicia a intent com a string final para mostrar
     */
    public void finalGame()
    {
        Intent i = new Intent(this,JogoFinal.class);
        String savedPalavras = readFromFile(getApplicationContext());
        i.putExtra("stringFinal",savedPalavras);
        overridePendingTransition(0, 0);
        startActivity(i);
        super.finish();
    }


}
