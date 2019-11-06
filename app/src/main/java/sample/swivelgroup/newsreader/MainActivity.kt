package sample.swivelgroup.newsreader

import android.os.AsyncTask
import android.support.design.widget.TabLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import sample.R

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    var dataList = ArrayList<HashMap<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        fetchJsonTopNewsData().execute();
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

    }

    inner class fetchJsonTopNewsData() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): String? {
            return URL("https://newsapi.org/v2/top-headlines?country=us&apiKey=062c722128c54b20bf7672916005d19c").readText(
                Charsets.UTF_8
            )
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.d("Fetched Data", result)
            findViewById<ProgressBar>(R.id.loader).visibility = View.GONE

            val jsonObj = JSONObject(result)
            val usersArr = jsonObj.getJSONArray("articles")
            val statusresul = jsonObj.getJSONArray("status")
            val totalResultsresult=jsonObj.getJSONArray("totalResults")
            for (i in 0 until usersArr.length()) {
                val singleUser = usersArr.getJSONObject(i)

                val map = HashMap<String, String>()
                map["newssourceid"] = singleUser.getJSONArray("source").getString(0)
                map["newsname"] = singleUser.getJSONArray("source").getString(1)
                map["newstitle"] = singleUser.getString("title")
                map["newsdesc"] = singleUser.getString("description")
                map["newsimageurl"] = singleUser.getString("urlToImage")
                map["newsurl"] = singleUser.getString("url")
                map["newspublishedAt"] = singleUser.getString("publishedAt")
                map["newscontent"] = singleUser.getString("content")

                dataList.add(map)
            }

            findViewById<ListView>(R.id.listView).adapter = CustomAdapter(this@MainActivity, dataList)
        }
    }

    inner class fetchJsonCustomeNewsData(val parasearch:String?) : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): String? {
            return URL("https://newsapi.org/v2/everything?q=bitcoin&apiKey=062c722128c54b20bf7672916005d19c").readText(
                Charsets.UTF_8
            )
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.d("Fetched Data", result)
            findViewById<ProgressBar>(R.id.loader).visibility = View.GONE

            val jsonObj = JSONObject(result)
            val usersArr = jsonObj.getJSONArray("articles")
            val statusresul = jsonObj.getJSONArray("status")
            val totalResultsresult=jsonObj.getJSONArray("totalResults")
            for (i in 0 until usersArr.length()) {
                val singleUser = usersArr.getJSONObject(i)

                val map = HashMap<String, String>()
                map["newssourceid"] = singleUser.getJSONArray("source").getString(0)
                map["newsname"] = singleUser.getJSONArray("source").getString(1)
                map["newstitle"] = singleUser.getString("title")
                map["newsdesc"] = singleUser.getString("description")
                map["newsimageurl"] = singleUser.getString("urlToImage")
                map["newsurl"] = singleUser.getString("url")
                map["newspublishedAt"] = singleUser.getString("publishedAt")
                map["newscontent"] = singleUser.getString("content")
                dataList.add(map)
            }

            findViewById<ListView>(R.id.listView).adapter = CustomAdapter(this@MainActivity, dataList)


        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_main, container, false)
           // rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))

            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
