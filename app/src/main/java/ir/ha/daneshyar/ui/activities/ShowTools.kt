package ir.formol.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.formol.R
import ir.ha.daneshyar.ui.fragments.AverageFragment
import ir.ha.daneshyar.ui.fragments.EventFragment
import ir.ha.daneshyar.ui.fragments.MainFragment
import ir.ha.daneshyar.ui.fragments.UnitSelectionFragment

class ShowTools : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_tools_items)

        val tag = intent.extras?.getString(MainFragment.TAG)

        when(tag){

            MainFragment.AVERAGE_ITEM -> { supportFragmentManager.beginTransaction().replace(R.id.toolsFrame,
                AverageFragment()
            ).commit() }
            MainFragment.EVENTS_ITEM -> { supportFragmentManager.beginTransaction().replace(R.id.toolsFrame,
                EventFragment()
            ).commit() }
            MainFragment.SELECTING_ITEM -> { supportFragmentManager.beginTransaction().replace(R.id.toolsFrame,
                UnitSelectionFragment()
            ).commit() }
            else -> {}
        }
    }
}
