package ir.formol.ui.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ir.formol.R
import ir.ha.daneshyar.models.PDFModel
import ir.ha.daneshyar.ui.fragments.MainFragment

class ShowPdfActivity : AppCompatActivity() {

    private lateinit var summaryBtn : FloatingActionButton
    private lateinit var toolbar: Toolbar
    private lateinit var pdfViewer: PDFView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_pdf)

        // ActionBar
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // initialize view
        summaryBtn = findViewById(R.id.summary_btn)
        pdfViewer = findViewById<PDFView>(R.id.PDFView)


        val pdfExtras = intent.getParcelableExtra<PDFModel>(MainFragment.PDF_EXTRA_KEY)

        pdfExtras.let { mPDF ->
            when(mPDF?.imageTitle){

                MainFragment.antegralSessionTag -> {
                    summaryBtn.visibility = View.VISIBLE
                    pdfViewer.fromAsset("antegral.pdf").load()
                    supportActionBar?.title = mPDF.imageTitle
                    summaryBtn.setOnClickListener{
                        summaryBtn.setImageResource(R.drawable.ic_baseline_settings_backup_restore_24)
                        pdfViewer.fromAsset("antegral2.pdf").load()
                        summaryBtn.setOnClickListener{
                            summaryBtn.visibility = View.GONE
                            pdfViewer.fromAsset("antegral.pdf").load()
                        }
                    }
                }


                MainFragment.derivativeSessionTag ->{
                    summaryBtn.visibility = View.INVISIBLE
                    pdfViewer.fromAsset("moshtag.pdf").load()
                    supportActionBar?.title = mPDF.imageTitle
                }

                MainFragment.areasSessionTag -> {
                    summaryBtn.visibility = View.INVISIBLE
                    pdfViewer.fromAsset("area.pdf").load()
                    supportActionBar?.title = mPDF.imageTitle
                }


                MainFragment.etehadSessionTag -> {
                    summaryBtn.visibility = View.INVISIBLE
                    pdfViewer.fromAsset("etehad.pdf").load()
                    supportActionBar?.title = mPDF.imageTitle
                }

                MainFragment.limitSessionTag -> {
                    summaryBtn.visibility = View.INVISIBLE
                    pdfViewer.fromAsset("had.pdf").load()
                    supportActionBar?.title = mPDF.imageTitle
                }

                MainFragment.sinAndCosSessionTag -> {
                    summaryBtn.visibility = View.INVISIBLE
                    pdfViewer.fromAsset("zavaya.pdf").load()
                    supportActionBar?.title = mPDF.imageTitle
                }

                else -> {Toast.makeText(this,"خطای نامشخص",Toast.LENGTH_SHORT).show()}
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->onBackPressed() }
        return super.onOptionsItemSelected(item)
    }
}
