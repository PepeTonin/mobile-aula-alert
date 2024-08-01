package com.example.aulaalert;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextView tituloValorTotal;
    TextInputEditText editTextValorTotal;
    TextView tvQntdePessoas;
    SeekBar seekBarQntdePessoas;
    TextView tvPorcGorjeta;
    SeekBar seekBarPorcGorjeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tituloValorTotal = (TextView) findViewById(R.id.tituloValorTotal);
        editTextValorTotal = (TextInputEditText) findViewById(R.id.editTextValorTotal);

        tvQntdePessoas = (TextView) findViewById(R.id.tvQntdePessoas);
        seekBarQntdePessoas = (SeekBar) findViewById(R.id.seekBarQntdePessoas);

        tvPorcGorjeta = (TextView) findViewById(R.id.tvPorcGorjeta);
        seekBarPorcGorjeta = (SeekBar) findViewById(R.id.seekBarPorcGorjeta);

        seekBarQntdePessoas.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvQntdePessoas.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarPorcGorjeta.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvPorcGorjeta.setText(Integer.toString(progress) + " %");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
    
    public void calcularValorPorPessoa(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Cálculo do valor por pessoa");
        dialog.setMessage("Escolha se irá pagar o valor com ou sem gorjeta.");
        dialog.setCancelable(true);
        dialog.setIcon(android.R.drawable.ic_dialog_info);
        dialog.setPositiveButton("Com Gorjeta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    float valorTotal = Float.parseFloat(editTextValorTotal.getText().toString());
                    int numPessoas = seekBarQntdePessoas.getProgress();
                    float porcGorjeta = ((float) seekBarPorcGorjeta.getProgress()) / 100;
                    if (numPessoas > 0) {
                        float valorPorPessoaComGorjeta = (valorTotal * (1 + porcGorjeta)) / numPessoas;
                        String valorFormatado = String.format("%.2f",valorPorPessoaComGorjeta);
                        Toast.makeText(getApplicationContext(),
                                "Valor: R$ " + valorFormatado,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Numero de pessoas deve ser maior que 0",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch(Exception error) {
                    Toast.makeText(getApplicationContext(),
                            "Preencha os campos corretamente",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setNegativeButton("Sem Gorjeta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    float valorTotal = Float.parseFloat(editTextValorTotal.getText().toString());
                    int numPessoas = seekBarQntdePessoas.getProgress();
                    if(numPessoas > 0) {
                        float valorPorPessoaSemGorjeta = valorTotal  / numPessoas;
                        String valorFormatado = String.format("%.2f",valorPorPessoaSemGorjeta);
                        Toast.makeText(getApplicationContext(),
                                "Valor: R$ " + valorFormatado,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Numero de pessoas deve ser maior que 0",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception error) {
                    Toast.makeText(getApplicationContext(),
                            "Preencha os campos corretamente",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.create();
        dialog.show();
    }
}