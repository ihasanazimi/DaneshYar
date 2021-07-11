package ir.formol.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator
import ir.formol.R
import ir.formol.models.PDFModel
import ir.formol.ui.activities.ShowPdfActivity
import ir.formol.ui.activities.ShowTools
import ir.formol.utils.PrefManager
import ir.formol.utils.adapters.HomeRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(), View.OnClickListener,
    HomeRecyclerViewAdapter.OnPdfClickListener {


    /** CONST */
    companion object {
        const val AVERAGE_ITEM = "AVERAGE_ITEM"
        const val SELECTING_ITEM = "SELECTING_ITEM"
        const val EVENTS_ITEM = "EVENTS_ITEM"
        const val TAG = "THIS_IS_MY_TAG"
        const val PDF_EXTRA_KEY = "PDF_EXTRA_KEY"
        const val antegralSessionTag = "دروس انتگرال"
        const val derivativeSessionTag = "دروس مشتق"
        const val areasSessionTag = "مساحت همه اشکال هندسی"
        const val etehadSessionTag = "اموزش تجزیه و اتحاد"
        const val limitSessionTag = "دروس حد و پیوستگی"
        const val sinAndCosSessionTag = "اموزش زوایای مثلثاتی"
    }


    // VIEWS
    private lateinit var eventItem: RelativeLayout
    private lateinit var selectingItem: RelativeLayout
    private lateinit var avgItem: RelativeLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var dots : IndefinitePagerIndicator
    private lateinit var openDrawerBtn : View
//    private lateinit var flowingDrawer: FlowingDrawer

    //CLASS`s
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        /** initialize layout views */
        initialize(view)

        /** change statusBar color */
        val window: Window = requireActivity().window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.color_waite)

        /** listeners */
        avgItem.setOnClickListener(this)
        eventItem.setOnClickListener(this)
        selectingItem.setOnClickListener(this)
        openDrawerBtn.setOnClickListener(this)

        return view
    }

    private fun initialize(view: View) {

        eventItem = view.findViewById(R.id.eventsItem)
        selectingItem = view.findViewById(R.id.selectingUnitItem)
        avgItem = view.findViewById(R.id.averageItem)
        dots = view.findViewById(R.id.dots)
        openDrawerBtn = view.findViewById(R.id.navDrawer)



//        flowingDrawer = view.findViewById(R.id.drawer)
//        flowingDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL)
//        flowingDrawer.setOnDrawerStateChangeListener(object : OnDrawerStateChangeListener {
//            override fun onDrawerStateChange(oldState: Int, newState: Int) {
//                if (newState == ElasticDrawer.STATE_CLOSED) {
//                    Log.i("MainActivity", "Drawer STATE_CLOSED")
//                }
//            }
//
//            override fun onDrawerSlide(openRatio: Float, offsetPixels: Int) {
//                Log.i("MainActivity", "openRatio=$openRatio ,offsetPixels=$offsetPixels")
//            }
//        })

        val aboutUsFragment = AboutUsFragment()
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.id_container_menu,aboutUsFragment,null).commit()

        prefManager = PrefManager(requireContext())

        recyclerView = view.findViewById(R.id.recyclerview)
        val list = arrayListOf<PDFModel>()
        list.apply {
            list.add(PDFModel("دروس انتگرال", R.drawable.ant))
            list.add(PDFModel("دروس مشتق", R.drawable.moshtagh))
            list.add(PDFModel("مساحت همه اشکال هندسی", R.drawable.area))
            list.add(PDFModel("اموزش تجزیه و اتحاد", R.drawable.etehad))
            list.add(PDFModel("دروس حد و پیوستگی", R.drawable.limit))
            list.add(PDFModel("اموزش زوایای مثلثاتی", R.drawable.sin))
        }
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        val adapter = HomeRecyclerViewAdapter(list, this)
        recyclerView.adapter = adapter
        val snapHelper1: SnapHelper = LinearSnapHelper()
        snapHelper1.attachToRecyclerView(recyclerView)
        dots.attachToRecyclerView(recyclerView)

    }

    override fun onClick(v: View?) {

        val intent = Intent(context, ShowTools::class.java)

        when (v!!.id) {

            averageItem.id -> {
                intent.putExtra(TAG, AVERAGE_ITEM)
                requireContext().startActivity(intent)
            }

            selectingUnitItem.id -> {
                intent.putExtra(TAG, SELECTING_ITEM)
                requireContext().startActivity(intent)
            }

            eventsItem.id -> {
                intent.putExtra(TAG, EVENTS_ITEM)
                requireContext().startActivity(intent)
            }

            R.id.navDrawer -> {

            }

            else -> {
            }


        }
    }




    override fun onPdfClick(pdfModel: PDFModel) {
        val intent = Intent(requireContext(),ShowPdfActivity::class.java)
        intent.putExtra(PDF_EXTRA_KEY,pdfModel)
        requireActivity().startActivity(intent)
    }
}

