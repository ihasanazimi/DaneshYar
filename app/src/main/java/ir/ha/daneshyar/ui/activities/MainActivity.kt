package ir.formol.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ir.formol.R
import ir.formol.databinding.ActivityMainBinding
import ir.ha.daneshyar.ui.fragments.MainFragment

class MainActivity : AppCompatActivity(){

    private lateinit var layout : ActivityMainBinding
    private lateinit var mainFragment : MainFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layout = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setContentView(layout.root)
        initialize()
    }

    private fun initialize() {
        /** variables init */
        mainFragment = MainFragment()
        supportFragmentManager.beginTransaction().replace(R.id.mainActivityContainer,mainFragment).commit()
    }


}
