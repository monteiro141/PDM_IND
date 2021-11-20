package pt.ubi.di.pmd.a44149_t9;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class MenuOpcoes extends MainActivity {
    Spinner numberOfRounds;
    Spinner numberOfPlayers;
    Spinner maxCharacters;
    Spinner maxWords;
    Switch randomOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuopcoes);
    }

    /**
     * Esta função devolve ao main activity os valores dos 4 spinners
     * @param v
     */
    public void goBack(View v)
    {
        Intent iResult = new Intent();
        numberOfRounds = (Spinner) findViewById(R.id.numberOfRoundsSId);
        iResult.putExtra("numOfRounds",((TextView) numberOfRounds.getSelectedView()).getText().toString());

        numberOfPlayers = (Spinner) findViewById(R.id.numberOfPlayersSId);
        iResult.putExtra("numOfPlayers",((TextView) numberOfPlayers.getSelectedView()).getText().toString());

        maxCharacters = (Spinner) findViewById(R.id.maxCharactersSId);
        iResult.putExtra("maxChars",((TextView) maxCharacters.getSelectedView()).getText().toString());

        maxWords = (Spinner) findViewById(R.id.maxWordsSId);
        iResult.putExtra("maxWords",((TextView) maxWords.getSelectedView()).getText().toString());

        randomOrder = (Switch) findViewById(R.id.randomOrderSId);
        iResult.putExtra("jogadorAle",randomOrder.isChecked());


        setResult(RESULT_OK,iResult);
        super.finish();
        overridePendingTransition(0, 0);
    }
}
