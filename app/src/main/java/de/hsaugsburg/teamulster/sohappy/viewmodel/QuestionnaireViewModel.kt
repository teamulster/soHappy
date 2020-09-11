package de.hsaugsburg.teamulster.sohappy.viewmodel

import androidx.lifecycle.ViewModel

/**
 * QuestionnaireViewModels contains all input that the user submitted during the
 * questionnaire phase.
 */
class QuestionnaireViewModel: ViewModel() {
    var questionnaire01Answer: Int = 0
    var questionnaire02Answer: Int = 0
    var questionnaire03Answer: Int = 0
    var questionnaire04Answer: Int = 0
    var questionnaire05Answer: Int = 0
    var questionnaire06Answer: Int = 0
}
