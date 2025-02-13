package com.ctrlz.beslim.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctrlz.beslim.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SignInFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signin_layout, container, false)
    }
}