package com.dnedev.photoeditor.navigation

import android.os.Bundle

sealed class NavigateTo
class GraphNav(val actionId: Int, var bundle: Bundle? = null) : NavigateTo()
