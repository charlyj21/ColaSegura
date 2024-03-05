package com.charlyj21.colasegura

import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class FirstViewLinkFragment : Fragment() {
    private var permissionMissing: Boolean = false
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_first_view_link, container, false)
        val btnLink1 = root.findViewById<Button>(R.id.btnLink1)


        btnLink1.setOnClickListener {
            testBluetooth()
        }

        return root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) { // Comprobar si es la solicitud de Bluetooth
            if (resultCode == RESULT_OK) {
                // Bluetooth habilitado
                permissionMissing = false // Actualizar estado del permiso
                findNavController().navigate(R.id.action_firstViewLinkFragment_to_secondViewLinkFragment)
            } else {
                // Bluetooth no habilitado
                Toast.makeText(requireContext(), "Bluetooth no habilitado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun testBluetooth() {
        val bluetoothManager = getSystemService(requireContext(), BluetoothManager::class.java)
        val bluetoothAdapter = bluetoothManager?.adapter
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, 1)
        }else{
            findNavController().navigate(R.id.action_firstViewLinkFragment_to_secondViewLinkFragment)
        }
    }
}