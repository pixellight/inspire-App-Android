package myapp.net.inspire.quiz

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_quiz.*
import myapp.net.inspire.R
import myapp.net.inspire.font.AvenirRoman

/**
 * Created by Alucard on 3/4/2019.
 */
class QuizFragment : Fragment() {
    private val questions = arrayOf(
            "People with EDS should NOT take naps",
            "It is best to go to bed and wake up at the same time every day",
            "Sitting still for long periods during the day will help you feel more alert",
            "For a more restful night’s sleep, skip your late afternoon cup of coffee",
            "Watching TV in bed can help you fall asleep")

    private val explainations = arrayOf(
            "Taking short (15–20 minutes), regularly scheduled naps can help with EDS.",
            "Keeping a regular sleep schedule lessen the symptoms of EDS.",
            "Sitting still for long periods can make you feel drowsy. Stand up, move around, or take a short walk to help you feel more alert.",
            "Because they are stimulants, The Division of Sleep Medicine at Harvard Medical School recommends avoiding coffee and other sources of caffeine after 4 pm.",
            "Keep your bedroom quiet and comfortable. Avoid stimulating activities such as watching TV in bed.")

    private val answers = arrayOf(
            "false",
            "true",
            "false",
            "true",
            "false"
    )

    private var currentQuestionNumber = 0
    private var canChangeAnswer = true


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.activity_quiz, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showQuestion(currentQuestionNumber)
        nextButton.setOnClickListener {
            goToNextQuestion()
        }
    
        val typeface = Typeface.createFromAsset(getContext().assets, "font/Avenir-Roman.otf")
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB ||
            android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
            trueButton.typeface = typeface
            falseButton.typeface = typeface
        }

    }

    private fun showQuestion(number: Int) {

        answerCorrect.visibility = View.GONE
        explaination.visibility = View.GONE
        nextButton.visibility = View.GONE

        questionNumber.text = "Question " + (number + 1) + " of " + questions.count()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            trueButton.background = activity!!.getDrawable(R.drawable.quiz_answer_unchecked)
            falseButton.background = activity!!.getDrawable(R.drawable.quiz_answer_unchecked)
        }

        question.text = questions[number]

        trueButton.setOnClickListener {
            if(canChangeAnswer) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    trueButton.background = activity!!.getDrawable(R.drawable.quiz_answer_checked)
                    falseButton.background = activity!!.getDrawable(R.drawable.quiz_answer_unchecked)
                }
                checkAnswer("true")
            }
        }

        falseButton.setOnClickListener {
            if(canChangeAnswer) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    trueButton.background = activity!!.getDrawable(R.drawable.quiz_answer_unchecked)
                    falseButton.background = activity!!.getDrawable(R.drawable.quiz_answer_checked)
                }
                checkAnswer("false")
            }
        }
    }

    private fun checkAnswer(clickedType: String) {
        canChangeAnswer = false

        if (answers[currentQuestionNumber] == clickedType) {
            //Correct Answer
            answerCorrect.text = "CORRECT!"
        } else {
            //Incorrect Answer
            answerCorrect.text = "INCORRECT!"
        }

        explaination.text = explainations[currentQuestionNumber]

        answerCorrect.visibility = View.VISIBLE
        explaination.visibility = View.VISIBLE
        if (currentQuestionNumber == 4) {
            nextButton.setImageResource(R.drawable.button_done)
        }
        nextButton.visibility = View.VISIBLE

    }

    private fun goToNextQuestion() {
    
        canChangeAnswer = true
        currentQuestionNumber += 1

        if (currentQuestionNumber == questions.count()) {
            activity!!.finish()
            return
        }

        showQuestion(currentQuestionNumber)

    }
    override fun onResume() {
        super.onResume()
        if (view == null) {
            return
        }

        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()

        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                return if (event!!.getAction() === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    activity!!.finish()
                    true
                } else false
            }

        })

    }

}