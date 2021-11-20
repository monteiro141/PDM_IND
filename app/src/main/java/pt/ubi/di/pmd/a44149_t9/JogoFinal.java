package pt.ubi.di.pmd.a44149_t9;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class JogoFinal extends Activity
{
    String finalString;
    TextView textoTV;
    TextToSpeech textToSpeech;
    ImageButton ttsB;
    @Override
    /**
     * Dentro do onCreate encontra-se o listener para o TextToSpeech
     * Essa função vai usar o TTS do telemovel no texto dentro do Textview
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jogofinal);
        Intent i = getIntent();
        finalString = i.getStringExtra("stringFinal");
        textoTV = findViewById(R.id.textoTVId);
        textoTV.setText(finalString);
        ttsB = findViewById(R.id.ttsBId);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        ttsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak(textoTV.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }

    /**
     * Função para voltar para trás
     * @param v
     */
    public void goBack(View v)
    {
        super.finish();
        overridePendingTransition(0, 0);
    }

}
