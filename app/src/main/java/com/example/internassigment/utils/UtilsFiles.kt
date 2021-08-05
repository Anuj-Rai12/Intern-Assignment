package com.example.internassigment.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.navArgs
import com.example.internassigment.R
import com.example.internassigment.data.WhyChoose
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern
import javax.inject.Inject
import kotlin.random.Random

const val TAG = "INTERNSHIP"

class MyDialog :
    androidx.fragment.app.DialogFragment() {
    private val args: MyDialogArgs by navArgs()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alterDialog = AlertDialog.Builder(requireActivity()).setTitle(args.title)
        alterDialog.setMessage(args.message)
            .setPositiveButton("ok") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
        return alterDialog.create()
    }
}

class CustomLog :
    androidx.fragment.app.DialogFragment() {
        private val args: CustomLogArgs by navArgs()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alterDialog = AlertDialog.Builder(requireActivity()).setTitle(args.title)
        alterDialog.setMessage(args.message)
            .setPositiveButton("LogOut") {_, _ ->
                FirebaseAuth.getInstance().signOut()
                activity?.finish()
            }
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
        return alterDialog.create()
    }
}

fun isValidPhone(phone: String): Boolean {
    val phonetic = "^[+]?[0-9]{10,13}\$"
    val pattern = Pattern.compile(phonetic)
    return pattern.matcher(phone).matches()
}

fun msg() = "The Good Password Must contain Following thing ;) :- \n\n" +
        "1.At least 1 digit i.e [0-9]\n" +
        "2.At least 1 lower case letter i.e [a-z]\n" +
        "3.At least 1 upper case letter i.e [A-Z]\n" +
        "4.Any letter i.e [A-Z,a-z]\n" +
        "5.At least 1 special character i.e [%^*!&*|)(%#$%]\n" +
        "6.No white spaces\n" +
        "7.At Least 8 Character\n"

fun isValidEmail(target: CharSequence?): Boolean {
    return if (target == null) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}

fun checkFieldValue(string: String) = string.isEmpty() || string.isBlank()

fun isValidPassword(password: String): Boolean {
    val passwordREGEX = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$"
    )
    return passwordREGEX.matcher(password).matches()
}

fun rand(from: Int = 1, to: Int = 30): Int {
    return Random.nextInt(to - from) + from
}

data class UserStore(
    val email: String,
    val password: String,
    val flag: Boolean
)

class CustomProgress @Inject constructor(private val customProgressBar: CustomProgressBar) {
    fun hideLoading() {
        customProgressBar.dismiss()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    fun showLoading(context: Context, string: String?, boolean: Boolean = false) {
        val con = context as Activity
        customProgressBar.show(con, string, boolean)
    }
}


const val Android = "Android Development"
const val web = "Web Development"
const val hacking = "Ethical Hacking"
const val digital_Art = "Digital Art"
const val Ui_Ux_Design = "UI/UX Design"
const val Auto_cad = "Auto CAD"
const val digitalMarket = "Digital Marketing"
const val GitAndGithub = "Git & GitHub"
const val How_to_Start = "How to Startup"
const val Python = "Python"

class UtilsFiles {
    companion object {
        val resFile by lazy {
            mapOf(
                Android to R.raw.science,
                web to R.raw.maths,
                hacking to R.raw.commerce,
                digital_Art to R.raw.arts,
                Ui_Ux_Design to R.raw.design,
                Auto_cad to R.raw.arthitecture,
                digitalMarket to R.raw.categroy_eight,
                GitAndGithub to R.raw.catogary_seven,
                How_to_Start to R.raw.catogroy_nine,
                Python to R.raw.catogroy_ten
            )
        }
        val whyChoose by lazy {
            mapOf(
                Android to listOf(
                    WhyChoose(
                        title = Androids.One.title,
                        description = Androids.One.desc
                    ),
                    WhyChoose(
                        title = Androids.Two.title,
                        description = Androids.Two.desc
                    ), WhyChoose(
                        title = Androids.Three.title,
                        description = Androids.Three.desc
                    )
                ),
                web to listOf(
                    WhyChoose(
                        title = WebSite.One.title,
                        description = WebSite.One.desc
                    ),
                    WhyChoose(
                        title = WebSite.Two.title,
                        description = WebSite.Two.desc
                    ), WhyChoose(
                        title = WebSite.Three.title,
                        description = WebSite.Three.desc
                    )
                ),
                hacking to listOf(
                    WhyChoose(
                        title = HackingCourse.One.title,
                        description = HackingCourse.One.desc
                    ),
                    WhyChoose(
                        title = HackingCourse.Two.title,
                        description = HackingCourse.Two.desc
                    ), WhyChoose(
                        title = HackingCourse.Three.title,
                        description = HackingCourse.Three.desc
                    )
                ),
                digital_Art to listOf(
                    WhyChoose(
                        title = DigitalArtCourse.One.title,
                        description = DigitalArtCourse.One.desc
                    ),
                    WhyChoose(
                        title = DigitalArtCourse.Two.title,
                        description = DigitalArtCourse.Two.desc
                    ), WhyChoose(
                        title = DigitalArtCourse.Three.title,
                        description = DigitalArtCourse.Three.desc
                    )
                ),
                Ui_Ux_Design to listOf(
                    WhyChoose(
                        title = UiUxCourse.One.title,
                        description = UiUxCourse.One.desc
                    ),
                    WhyChoose(
                        title = UiUxCourse.Two.title,
                        description = UiUxCourse.Two.desc
                    ), WhyChoose(
                        title = UiUxCourse.Three.title,
                        description = UiUxCourse.Three.desc
                    )
                ),
                Auto_cad to listOf(
                    WhyChoose(
                        title = AutoCADCourse.One.title,
                        description = AutoCADCourse.One.desc
                    ),
                    WhyChoose(
                        title = AutoCADCourse.Two.title,
                        description = AutoCADCourse.Two.desc
                    ), WhyChoose(
                        title = AutoCADCourse.Three.title,
                        description = AutoCADCourse.Three.desc
                    )
                ),
                digitalMarket to listOf(
                    WhyChoose(
                        title = DigitalMarketing.One.title,
                        description = DigitalMarketing.One.desc
                    ),
                    WhyChoose(
                        title = DigitalMarketing.Two.title,
                        description = DigitalMarketing.Two.desc
                    ), WhyChoose(
                        title = DigitalMarketing.Three.title,
                        description = DigitalMarketing.Three.desc
                    )
                ),
                GitAndGithub to listOf(
                    WhyChoose(
                        title = GitGitHubCourse.One.title,
                        description = GitGitHubCourse.One.desc
                    ),
                    WhyChoose(
                        title = GitGitHubCourse.Two.title,
                        description = GitGitHubCourse.Two.desc
                    ), WhyChoose(
                        title = GitGitHubCourse.Three.title,
                        description = GitGitHubCourse.Three.desc
                    )
                ),
                Python to listOf(
                    WhyChoose(
                        title = WhyPythonCourse.One.title,
                        description = WhyPythonCourse.One.desc
                    ),
                    WhyChoose(
                        title = WhyPythonCourse.Two.title,
                        description = WhyPythonCourse.Two.desc
                    ), WhyChoose(
                        title = WhyPythonCourse.Three.title,
                        description = WhyPythonCourse.Three.desc
                    )
                ),
                How_to_Start to listOf(
                    WhyChoose(
                        title = WhyStartUpCourse.One.title,
                        description = WhyStartUpCourse.One.desc
                    ),
                    WhyChoose(
                        title = WhyStartUpCourse.Two.title,
                        description = WhyStartUpCourse.Two.desc
                    ), WhyChoose(
                        title = WhyStartUpCourse.Three.title,
                        description = WhyStartUpCourse.Three.desc
                    )
                ),
            )
        }
    }
}

object Androids {

    object One {
        const val title = "Popularity"
        const val desc =
            "With 2.6Mn apps on Play Store and 75 Billion downloads a year, Android App Development is one of the most popular skills today."
    }

    object Two {
        const val title = "Build your own App"
        const val desc =
            "Imagine an app on Play Store under your name. There is nothing more exciting than that!"
    }

    object Three {
        const val title = "Lucrative Salary Android"
        const val desc = "The Android Developers earn as high as 9 LPA+ according to GlassDoor."
    }
}

object WebSite {
    object One {
        const val title = "Build Awesome Websites"
        const val desc =
            "Mark Zuckerberg built Facebook. Sachin Bansal built Flipkart. What will you build?"
    }

    object Two {
        const val title = "Be in Demand"
        const val desc =
            "With 1.7 billion websites on the internet, it is one of the hottest career options with an average fresher salary of 6 LPA for full stack developers according to GlassDoor."
    }

    object Three {
        const val title = "Eat Sleep Code Repeat"
        const val desc = "Be it rain or sunshine, coding is always on your mind."
    }
}

object HackingCourse {
    object One {
        const val title = "Because It Fun"
        const val desc =
            "Explore different design concepts endlessly, create your own website to sell your art, or work for a design studio - the possibilities are endless!"
    }

    object Two {
        const val title = "Be in Demand"
        const val desc =
            "With the entire world and its data coming online, the demand for cyberSecurity experts is skyrocketing."
    }

    object Three {
        const val title = "Lucrative Salary"
        const val desc =
            "The average salary of a CyberSecurity Analyst is 6.8 LPA+ according to Indeed."
    }
}

object DigitalArtCourse {
    object One {
        const val title = "Freedom"
        const val desc =
            "Explore different design concepts endlessly, create your own website to sell your art, or work for a design studio - the possibilities are endless!"
    }

    object Two {
        const val title = "Easy on the pocket"
        const val desc =
            "No need to invest in high quality brushes, paints, inks, and paper that need to be replenished every time. Digital art is way cheaper than traditional art!"
    }

    object Three {
        const val title = "Art is your whole world"
        const val desc =
            "From studying the works of greats like Picasso and Van Gogh to day dreaming about your next creation, art is life, and life is art."
    }
}

object UiUxCourse {
    object One {
        const val title = "Design The Future"
        const val desc =
            "Be it the next Snapchat, smartwatch, self-driving cars, virtual reality apps, or drone deliveries - UI/UX designers are shaping the future."
    }

    object Two {
        const val title = "Help the world"
        const val desc =
            "Design is not just how it looks. Design is how it works. UI/UX designers make the world a better place - one app at a time."
    }

    object Three {
        const val title = "Lucrative Salary"
        const val desc =
            "No wonder, the average salary for a UI/UX designer is 9 LPA+ according to GlassDoor."
    }
}

object AutoCADCourse {
    object One {
        const val title = "The most widely used CAD software"
        const val desc =
            "Since its launch in 1982, AutoCAD is the most widely used CAD software by engineers, architects, interior designers, etc."
    }

    object Two {
        const val title = "Design the next BMW or Burj Khalifa"
        const val desc =
            "Whether you like numbers, or wordplay, or designing things; digital marketing offers a heady mix of creativity for everyone."
    }

    object Three {
        const val title = "Design quickly edit easily"
        const val desc =
            "Become a pro at design. Creating and editing designs in AutoCAD is quick and hassle free."
    }
}


object DigitalMarketing {
    object One {
        const val title = "Take your idea to millions"
        const val desc =
            "Machine Learning, AI, Web Development, Hacking, IoT, and more. Python is used everywhere."
    }

    object Two {
        const val title = "Channel your Creativity"
        const val desc =
            "Whether you like numbers, or wordplay, or designing things; digital marketing offers a heady mix of creativity for everyone."
    }

    object Three {
        const val title = "Be in demand digital market"
        const val desc =
            "Digital marketing is one of the hottest career options these days with thousands of internships and jobs being added everyday."
    }
}

object GitGitHubCourse {
    object One {
        const val title = "A developer best friend"
        const val desc =
            "Machine Learning, AI, Web Development, Hacking, IoT, and more. Python is used everywhere."
    }

    object Two {
        const val title = "Powerful community"
        const val desc =
            "Host your code online, meet other developers, ask questions and more on GitHub - the largest host of source code in the world!"
    }

    object Three {
        const val title = "Be in demand"
        const val desc =
            "Google, Amazon, Microsoft, and other companies around the world use Git and GitHub for collaboration. So, if you know Git and GitHub, consider yourself a cut above the rest!"
    }
}

object WhyPythonCourse {
    object One {
        const val title = "Versatility"
        const val desc =
            "Machine Learning, AI, Web Development, Hacking, IoT, and more. Python is used everywhere."
    }

    object Two {
        const val title = "Beginner friendly"
        const val desc =
            "A simple and powerful syntax makes Python one of the easiest languages to learn."
    }

    object Three {
        const val title = "Be in Demand"
        const val desc =
            "Python is the fastest growing language according to Stack Overflow with an average fresher salary of 5 LPA+ according to GlassDoor."
    }
}

object WhyStartUpCourse {
    object One {
        const val title = "Be your own boss"
        const val desc =
            "You take risks, you call the shots, and you decide how you want to work. You are truly in control of your own destiny!"
    }

    object Two {
        const val title = "Next BIG thing"
        const val desc = "Who knows, you may build the next Amazon, Apple, or Facebook!"
    }

    object Three {
        const val title = "Always Follow your own Heart"
        const val desc =
            "And make a difference! There is no better feeling than working on something that makes you happy."
    }
}