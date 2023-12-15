package com.example.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.texttospeech.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var textToSpeech:TextToSpeech?=null
    private var binding : ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding =ActivityMainBinding.inflate((layoutInflater))
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        textToSpeech=TextToSpeech(this,this)

        binding?.speechBtn?.setOnClickListener{view->
            if(binding?.speechEt?.text!!.isEmpty()){
                Toast.makeText(this,"Please Enter The Text",
                    Toast.LENGTH_LONG).show()
            }
            else{
                speakOut(binding?.speechEt?.text.toString())
            }
        }
    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            val result=textToSpeech?.setLanguage(Locale.UK)
            if(result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Language is not Supported")
            }
            else{
                Log.e("TTS","Cant Initialized")
            }
        }
    }
    private fun speakOut(text : String){
        textToSpeech?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onDestroy(){
        super.onDestroy()
            if(textToSpeech != null) {
                textToSpeech?.stop()
                textToSpeech?.shutdown()
            }
        binding =null
    }
}