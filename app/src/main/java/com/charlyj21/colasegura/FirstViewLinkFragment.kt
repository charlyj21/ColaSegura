package com.charlyj21.colasegura


import android.Manifest
import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.io.IOException
import java.util.UUID


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
                //connectToBluetooth()
                connectToBluetoothDevice()
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
                //connectToBluetooth()
                connectToBluetoothDevice()
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

            //
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, CODE_ON_BT)
            return
        } else {
            //connectToBluetooth()
            connectToBluetoothDevice()
            return
        }
    }

    private fun connectToBluetooth(){
        findNavController().navigate(R.id.action_firstViewLinkFragment_to_secondViewLinkFragment)
    }

    private fun connectToBluetoothDevice() {
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
            }

            //
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, CODE_ON_BT)
        }

        val deviceList = bluetoothAdapter?.bondedDevices
        val specifiedDevice = deviceList?.find { it.name == "HC-05" }

        // Crear un objeto BluetoothSocket para la conexión
        val uuidString = "00001101-0000-1000-8000-00805F9B34FB"
        val uuid = UUID.fromString(uuidString)
        val socket: BluetoothSocket? = specifiedDevice?.createRfcommSocketToServiceRecord(uuid)

        // Intentar conectar al dispositivo
        try {
            socket?.connect()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}