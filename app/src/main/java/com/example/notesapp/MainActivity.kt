package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.notesapp.fragments.CalendarFragment
import com.example.notesapp.fragments.CreateNoteFragment
import com.example.notesapp.fragments.MainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)

        val bottomNav : BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNav.setOnNavigationItemSelectedListener {
            lateinit var fragment : Fragment
            when(it.itemId){
                R.id.createNote ->{
                    fragment = CreateNoteFragment()
                    loadFragment(fragment)
                    true
                }

                R.id.calendar ->{
                    fragment = CalendarFragment()
                    loadFragment(fragment)
                    true
                }

                R.id.appHome ->{
                    fragment = MainFragment()
                    loadFragment(fragment)
                    true
                }
                else -> false
            }

        }

    }

    public fun loadFragment(_fragment : Fragment)
    {

        val currentFragment = findCurrentFragment()
        val _fragmentManager : FragmentManager = supportFragmentManager

        _fragmentManager.beginTransaction()
            .replace(currentFragment.id,_fragment)
            .commit()

    }

    public fun findCurrentFragment():Fragment{

        lateinit var currentFragment : Fragment
        val _fragmentManager : FragmentManager = supportFragmentManager
        val _fragments : List<Fragment> = _fragmentManager.fragments
        for(x in _fragments)
        {
            if(x.isVisible)
                currentFragment=x
        }
        return currentFragment
    }
}
