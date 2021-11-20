package pt.ubi.di.pmd.a44149_t9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String LOGCATTAG = "DEMO";
    public static final int REQUEST_CODE = 1;
    public int numRondas;
    public int numJogadores;
    public int maxChars;
    public int maxPalavras;
    public boolean jogadorAleatorio;

    Button jogoB;
    Button menuOpcoesB;
    Button regrasB;
    Button sairB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        numJogadores=2;
        numRondas=5;
        maxChars=140;
        maxPalavras=2;
        jogadorAleatorio=false;
    }
    boolean doubleBackToExitPressedOnce = false;

    /**
     * Esta função verifica se a pessoa carregou 2x no "back"
     */
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    /**
     * Vai começar a intent para o menu opções
     * @param v
     */
    public void activityMenuOpcoes(View v)
    {
        Intent i = new Intent(this,MenuOpcoes.class);
        overridePendingTransition(0, 0);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(i,REQUEST_CODE);

    }

    /**
     * Vai esperar o resultado do menu opções para mudar as variaveis numrondas numjogadores
     * maxchars maxpalavras e jogadorAleatorio
     * @param requestCode
     * @param resultCode
     * @param i
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        super.onActivityResult(requestCode, resultCode, i);
        if ((requestCode == REQUEST_CODE) && (resultCode == RESULT_OK)) {
            numRondas = Integer.parseInt(i.getStringExtra("numOfRounds"));
            numJogadores = Integer.parseInt(i.getStringExtra("numOfPlayers"));
            maxChars = Integer.parseInt(i.getStringExtra("maxChars"));
            maxPalavras = Integer.parseInt(i.getStringExtra("maxWords"));
            jogadorAleatorio = i.getBooleanExtra("jogadorAle",false);
            /*Log.i(LOGCATTAG, String.valueOf(numRondas));
            Log.i(LOGCATTAG, String.valueOf(numJogadores));
            Log.i(LOGCATTAG, String.valueOf(maxChars));
            Log.i(LOGCATTAG, String.valueOf(maxPalavras));
            Log.i(LOGCATTAG, String.valueOf(jogadorAleatorio));*/

        }
    }

    /**
     * Vai começar uma nova intent para a atividade Jogo com as variaveis numrondas numjogadores
     * maxchars maxpalavras e jogadorAleatorio
     * @param v
     */
    public void mainJogar(View v)
    {
        Intent i = new Intent(this,Jogo.class);
        i.putExtra("numJog",String.valueOf(numJogadores));
        i.putExtra("numRond",String.valueOf(numRondas));
        i.putExtra("maxChars",String.valueOf(maxChars));
        i.putExtra("maxPal",String.valueOf(maxPalavras));
        i.putExtra("jogadorAle",jogadorAleatorio);
        startActivity(i);
        overridePendingTransition(0, 0);
    }

    /**
     * Vai começar a intent para a atividade Regras
     * @param v
     */
    public void mainRegras(View v)
    {
        Intent i = new Intent(this,Regras.class);
        startActivity(i);
        overridePendingTransition(0, 0);
    }

    /**
     * Fecha o programa
     * @param v
     */
    public void mainExit(View v)
    {
        super.finish();
    }
}