package myapp.net.inspire.report

import android.Manifest
import android.accounts.AccountManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.util.Patterns
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.common.AccountPicker
import kotlinx.android.synthetic.main.mail_report.*
import myapp.net.inspire.R








/**
 * Created by deadlydragger on 10/28/18.
 */
class EmailReportActivity : AppCompatActivity() {
    var requestcode: Int? = 101
    private var promptedForAccount = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.mail_report)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var title = findViewById<TextView>(R.id.title)
        val titleRight = findViewById<TextView>(R.id.titleRight)
        toolbar.visibility = View.VISIBLE
        title.visibility = View.VISIBLE
        title.text = "inspire+ Report"
        titleRight.text = "Send"

        if(android.os.Build.VERSION.SDK_INT > 22){
//            if(isGETACCOUNTSAllowed()){
//
//                return;
//            }else{
//                requestGET_ACCOUNTSPermission();
//            }

            if (!promptedForAccount) {
                promptedForAccount = true
                val intent = AccountPicker.newChooseAccountIntent(null, null,
                       arrayOf (GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE ), false, null, null, null,null);
                startActivityForResult(intent, requestcode!!);
            }

        }else{
            getMailAddress()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int,  permissions: Array<String>, grantResults: IntArray) {

        if (requestCode == requestcode) {

            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                Toast.makeText(this, "Thank You For Permission Granted ", Toast.LENGTH_LONG).show()

                getMailAddress()

            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun getMailAddress() {

        var possibleEmail: String? = null

        val emailPattern = Patterns.EMAIL_ADDRESS
        val accounts = AccountManager.get(this@EmailReportActivity).getAccountsByType(
                "com.google")
        for (account in accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                possibleEmail = account.name
                Log.i("MY_EMAIL_count", "" + possibleEmail!!)
                fromEmailEditText?.setText(possibleEmail!!)
            }
        }

    }

    private fun isGETACCOUNTSAllowed(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS)

        return if (result == PackageManager.PERMISSION_GRANTED) true else false

    }


    private fun requestGET_ACCOUNTSPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.GET_ACCOUNTS)) {


        }

        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.GET_ACCOUNTS), requestcode!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestcode) {
            val accountName = data?.extras?.get(AccountManager.KEY_ACCOUNT_NAME) ?: ""
            Log.d("MainActivity", "Selected account: $accountName")
            fromEmailEditText?.setText(accountName.toString())
        }
    }
}