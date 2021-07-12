package ir.ha.daneshyar.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.formol.R
import ir.ha.daneshyar.utils.Utilities
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class AboutUsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return AboutPage(container?.context)
            .isRTL(false)
            .setDescription("Contact Us..")
            .setImage(R.drawable.logo2)
            .addEmail("ihasan.azimi@gmail.com")
            .addInstagram("ihasan.azimi")
            .addItem(sendMessage())
            .addItem(rate())
            .addItem(developerApps())
            .addItem(getAppVersionElement())
            .create()
    }


    private fun sendMessage() : Element{
        val element = Element()
        element.title = "Message | SMS"
        element.iconDrawable = R.drawable.ic_comment_black_24dp
        element.iconTint = R.color.colorAccent
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("sms:09397106611")
        element.intent = intent
        return element
    }


    private fun getAppVersionElement() : Element{
        val versionElement = Element()
        versionElement.iconDrawable = R.drawable.ic_baseline_info_24
        versionElement.iconTint = R.color.color_gray
        versionElement.title = "Version " + ir.formol.BuildConfig.VERSION_NAME
        return versionElement
    }

    private fun rate() : Element{
        val pm = requireContext().packageManager
        val intent = Intent(Intent.ACTION_EDIT)
        intent.data = Uri.parse("bazaar://details?id=ir.formol")
        intent.setPackage("com.farsitel.bazaar")

        val v = Element()
        v.iconDrawable = R.drawable.ic_baseline_stars_24
        v.iconTint = R.color.color_yellow
        v.title = "Rate Us"
        if (Utilities.isPackageInstalled("com.farsitel.bazaar",pm)) {
            v.intent = intent
        }

        return v
    }


    private fun developerApps() : Element{

        val pm = requireContext().packageManager
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("bazaar://collection?slug=by_author&aid=hasanazimi")
        intent.setPackage("com.farsitel.bazaar")

        val v = Element()
        v.iconDrawable = R.drawable.ic_baseline_apps_24
        v.iconTint = R.color.colorAccent
        v.title = "Other applications"
        if (Utilities.isPackageInstalled("com.farsitel.bazaar",pm)) {
            v.intent = intent
        }
        return v
    }




}


