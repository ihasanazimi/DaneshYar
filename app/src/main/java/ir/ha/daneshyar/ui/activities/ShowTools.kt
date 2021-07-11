package ir.formol.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.formol.R
import ir.formol.ui.fragments.AverageFragment
import ir.formol.ui.fragments.EventFragment
import ir.formol.ui.fragments.MainFragment
import ir.formol.ui.fragments.UnitSelectionFragment

class ShowTools : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_tools_items)

        val tag = intent.extras?.getString(MainFragment.TAG)

        when(tag){

            MainFragment.AVERAGE_ITEM -> { supportFragmentManager.beginTransaction().replace(R.id.toolsFrame,AverageFragment()).commit() }
            MainFragment.EVENTS_ITEM -> { supportFragmentManager.beginTransaction().replace(R.id.toolsFrame,EventFragment()).commit() }
            MainFragment.SELECTING_ITEM -> { supportFragmentManager.beginTransaction().replace(R.id.toolsFrame,UnitSelectionFragment()).commit() }
            else -> {}
        }
    }
}
