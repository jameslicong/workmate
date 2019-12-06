package com.workmate.attendance.ui.screens.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.Window
import android.widget.TextView
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.workmate.attendance.R
import io.reactivex.disposables.Disposable

class AttendanceEntryDialog(private val builder: Builder) : Dialog(builder.context)  {

    @BindView(R.id.title)
    lateinit var title: TextView

    @BindView(R.id.cancel_button)
    lateinit var cancelBtn: TextView

    @BindString(R.string.cancel)
    lateinit var cancelText: String

    @OnClick(R.id.cancel_button)
    internal fun onClickCancel() {
        dismiss()
    }

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.partial_attendance_confirmation_dialog)
        ButterKnife.bind(this)


        setCancelable(false)

        val underlinedText = SpannableString(cancelText)
        underlinedText.setSpan(UnderlineSpan(), 0, underlinedText.length, 0)
        cancelBtn.text = underlinedText

    }




    data class Builder(
        var context: Context? = null,
        var action: Unit) {

        private var callback: Unit
        private lateinit var titleText: String

        init {
            this.context = context
            this.callback = action
        }

        fun setTitle(text: String): Builder {
            this.titleText = text
            return this
        }

        fun build() : AttendanceEntryDialog {
            return AttendanceEntryDialog(this)
        }

    }
}