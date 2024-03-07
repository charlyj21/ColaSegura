package com.charlyj21.colasegura

import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.Manifest
import androidx.core.content.ContextCompat


class FirstViewLinkFragment : Fragment() {
    private var permissionMissing: Boolean = false
    private val CODE_ON_BT = 1
    private val CODE_PM_BT = 3
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

        if (requestCode == CODE_ON_BT) { // Comprobar si es la solicitud de Bluetooth
            if (resultCode == RESULT_OK) {
                //test
                connectToBluetooth()
            } else {
                // Bluetooth no habilitado
                Toast.makeText(requireContext(), "Bluetooth no habilitado", Toast.LENGTH_SHORT).show()
            }
        }

        if (requestCode == 2) { // Comprobar la conexion con el dispositivo
            if (resultCode == RESULT_OK) {
                // Bluetooth habilitado
                permissionMissing = false // Actualizar estado del permiso
                findNavController().navigate(R.id.action_firstViewLinkFragment_to_secondViewLinkFragment)
            } else {
                // Bluetooth no habilitado
                Toast.makeText(requireContext(), "Dispositivo no encontrado", Toast.LENGTH_SHORT).show()
            }
        }

        if (requestCode == CODE_PM_BT) { // Comprobar si es la solicitud de Bluetooth
            if (resultCode == RESULT_OK) {
                //test
                connectToBluetooth()
            } else {
                // Bluetooth no habilitado
                Toast.makeText(requireContext(), "Permiso no habilitado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun testBluetooth() {
        val bluetoothManager = getSystemService(requireContext(), BluetoothManager::class.java)
        val bluetoothAdapter = bluetoothManager?.adapter
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                    CODE_PM_BT
                )
                return
            }

            // Solicitar habilitación de Bluetooth
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, CODE_ON_BT)
            return
        } else {
            connectToBluetooth()
            return
        }
    }

    private fun connectToBluetooth(){
        findNavController().navigate(R.id.action_firstViewLinkFragment_to_secondViewLinkFragment)
        //val intent = Intent(BluetoothDevice.ACTION_PAIRING_REQUEST)
        //val bluetoothDevice = "98:D3:61:F5:C1:4A"
        //intent.putExtra(BluetoothDevice.EXTRA_DEVICE, bluetoothDevice)
        //intent.putExtra(BluetoothDevice.EXTRA_PAIRING_VARIANT, BluetoothDevice.PAIRING_VARIANT_PIN)
        //startActivityForResult(intent, 2)
    }
}