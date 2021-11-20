package pt.ubi.di.pmd.a44149_t9;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Regras extends MainActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regras);
    }

    /**
     * Função para voltar para trás
      * @param v
     */
    public void goBack(View v)
    {
        super.finish();
    }
}
