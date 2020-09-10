package de.hsaugsburg.teamulster.sohappy.viewmodel

import androidx.lifecycle.ViewModel

class QuestionnaireViewModel: ViewModel() {
    var questionnaire01Answer: Int = 0
    var questionnaire02Answer: Int = 0
    var questionnaire03Answer: Int = 0
    var questionnaire04Answer: Int = 0
    var questionnaire05Answer: Int = 0
    var questionnaire06Answer: Int = 0

    /* init {
        Log.i(this.javaClass.name, "ViewModel was created.")
        Log.i(this.javaClass.name, "ViewModel has managed to save Questionnaire01's value: " + questionnaire01Answer)
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(this.javaClass.name, "ViewModel was destroyed.")
    } */
}
