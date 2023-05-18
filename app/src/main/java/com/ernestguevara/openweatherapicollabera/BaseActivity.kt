package com.ernestguevara.openweatherapicollabera

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ernestguevara.openweatherapicollabera.databinding.DialogPopupBinding
import com.ernestguevara.openweatherapicollabera.presentation.components.LoadingDialog

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var loadingDialog: LoadingDialog

    lateinit var alertDialog: AlertDialog
    var isDialogShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadingDialog = LoadingDialog(this)
    }

    fun showLoadingDialog() {
        loadingDialog.show()
    }

    fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    fun showAlertDialog(
        context: Context,
        title: String?,
        message: String?,
        primaryBtnText: String?,
        primaryClickListener: View.OnClickListener? = null,
        secondaryBtnText: String?,
        secondaryClickListener: View.OnClickListener? = null,
        canDismiss: Boolean = true
    ) {
        if (!isDialogShown) {
            isDialogShown = true
            val builder = AlertDialog.Builder(context)
            val binding = DialogPopupBinding.inflate(LayoutInflater.from(context))
            val layout = binding.root

            binding.run {
                if (title == null) {
                    alertMessage.setTypeface(null, Typeface.BOLD)
                } else {
                    alertTitle.visibility = View.VISIBLE
                }
                alertMessage.visibility = View.VISIBLE
                alertTitle.text = title
                alertMessage.text = message
                alertMessage.movementMethod = ScrollingMovementMethod.getInstance()

                if (!TextUtils.isEmpty(primaryBtnText)) {
                    primaryButton.text = primaryBtnText
                    primaryButton.visibility = View.VISIBLE
                }

                if (!TextUtils.isEmpty(secondaryBtnText)) {
                    secondaryButton.text = secondaryBtnText
                    secondaryButton.visibility = View.VISIBLE
                }

                builder.setView(layout)
                    .setCancelable(canDismiss)
                alertDialog = builder.create()

                primaryButton.setOnClickListener { v: View? ->
                    isDialogShown = false
                    alertDialog.dismiss()
                    primaryClickListener?.onClick(v)
                }

                secondaryButton.setOnClickListener { v: View? ->
                    isDialogShown = false
                    alertDialog.dismiss()
                    secondaryClickListener?.onClick(v)
                }
                alertDialog.setOnDismissListener {
                    isDialogShown = false
                }

                alertDialog.show()
            }
        }
    }

    fun closeAlertDialog() {
        if (alertDialog.isShowing) {
            isDialogShown = false
            alertDialog.dismiss()
        }
    }
}