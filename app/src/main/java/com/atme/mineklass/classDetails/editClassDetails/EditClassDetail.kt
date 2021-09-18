package com.atme.mineklass.classDetails.editClassDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.atme.mineklass.Constants
import com.atme.mineklass.R
import com.atme.mineklass.classData.ClassData
import com.atme.mineklass.databinding.EditClassDetailFragmentBinding
import com.atme.mineklass.utils.getTime
import com.atme.mineklass.utils.getTimeString
import timber.log.Timber

// TODO add validation
class EditClassDetail : Fragment() {

    private lateinit var binding: EditClassDetailFragmentBinding
    private lateinit var newClassData: ClassData

    private val viewModel: EditClassDetailViewModel by lazy {
        ViewModelProvider(this).get(EditClassDetailViewModel::class.java)
    }

    private var startHour: Int? = null
    private var startMin: Int? = null
    private var endHour: Int? = null
    private var endMin: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val selectedClassId = EditClassDetailArgs.fromBundle(requireArguments()).id

        binding =
            DataBindingUtil.inflate(inflater, R.layout.edit_class_detail_fragment, container, false)

        binding.saveButton.setOnClickListener {
            val startTime = getTimeString(binding.fromTime.hour, binding.fromTime.minute)
            val endTime = getTimeString(binding.toTime.hour, binding.toTime.minute)

            val classTime = "$startTime - $endTime"

            val className = binding.className.text.toString()
            val teacher = binding.teacherName.text.toString()
            val day = binding.daySpinner.selectedItem.toString()
            val room = binding.classRoom.text.toString()
            val block = binding.classBlock.text.toString()
            val group = binding.classGroup.text.toString()
            val moduleCode = binding.moduleCode.text.toString()
            val classType = binding.classType.selectedItem.toString()

            if (selectedClassId != Constants.ID_EDIT) {
                newClassData = ClassData(
                    id = selectedClassId.toInt(),
                    day = day,
                    time = classTime,
                    class_type = classType,
                    module_name = moduleCode,
                    module_title = className,
                    lecturer = teacher,
                    group = group,
                    block = block,
                    room = room
                )
            }
            else{
                newClassData = ClassData(
                    id = -1,
                    day = day,
                    time = classTime,
                    class_type = classType,
                    module_name = moduleCode,
                    module_title = className,
                    lecturer = teacher,
                    group = group,
                    block = block,
                    room = room
                )
            }

            if (selectedClassId != Constants.ID_EDIT) {
                Timber.e("Edit class called")
                viewModel.saveClass(newClassData)
            } else {
                Timber.e("Save class called")
                viewModel.addNewClass(newClassData)
            }
        }

        binding.fromTime.setOnTimeChangedListener { _, hourOfDay, minute ->
            startHour = hourOfDay
            startMin = minute
        }

        binding.toTime.setOnTimeChangedListener { _, hourOfDay, minute ->
            endHour = hourOfDay
            endMin = minute
        }

        viewModel.insertToDatabase.observe(viewLifecycleOwner) {
            if (it == true) {
                viewModel.doneInsert()
                findNavController().navigate(EditClassDetailDirections.editToDetail(newClassData))
            }
        }

        viewModel.currentClass.observe(viewLifecycleOwner) {
            if (it != null) {
                populateFields(it)
            }
        }

        if (savedInstanceState != null) {
            if (savedInstanceState[Constants.START_HOUR] != null) {
                binding.fromTime.hour = savedInstanceState.getInt(Constants.START_HOUR)
            }
            if (savedInstanceState[Constants.START_MIN] != null) {
                binding.fromTime.minute = savedInstanceState.getInt(Constants.START_MIN)
            }
            if (savedInstanceState[Constants.END_HOUR] != null) {
                binding.toTime.hour = savedInstanceState.getInt(Constants.END_HOUR)
            }
            if (savedInstanceState[Constants.END_MIN] != null) {
                binding.toTime.minute = savedInstanceState.getInt(Constants.END_MIN)
            }

        }

        // Adapter for days
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.days_array,
            R.layout.spinner_item_layout
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_layout)
            binding.daySpinner.adapter = adapter
        }

        // Adapter for class type
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.class_type_array,
            R.layout.spinner_item_layout
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_layout)
            binding.classType.adapter = adapter
        }

        if (startHour == null) {
            if (selectedClassId != Constants.ID_EDIT) {
                viewModel.startPopulateFields(selectedClassId.toInt())
            }

        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        startHour?.let {
            outState.putInt(Constants.START_HOUR, it)
        }

        startMin?.let {
            outState.putInt(Constants.START_MIN, it)
        }

        endHour?.let {
            outState.putInt(Constants.END_HOUR, it)
        }

        endMin?.let {
            outState.putInt(Constants.END_MIN, it)
        }
    }

    private fun populateFields(selectedClass: ClassData) {
        binding.className.setText(selectedClass.module_title)
        binding.teacherName.setText(selectedClass.lecturer)
        binding.classRoom.setText(selectedClass.room)
        binding.classBlock.setText(selectedClass.block)
        binding.classGroup.setText(selectedClass.group)
        binding.moduleCode.setText(selectedClass.module_name)

        val classTime = getTime(selectedClass.time)
        val classStartTime = classTime[0]
        val classEndTime = classTime[1]

        startHour = classStartTime.hour

        binding.fromTime.hour = classStartTime.hour
        binding.fromTime.minute = classStartTime.minute
        binding.toTime.hour = classEndTime.hour
        binding.toTime.minute = classEndTime.minute

        binding.daySpinner.setSelection(Constants.DAYS.indexOf(selectedClass.day))
        binding.classType.setSelection(Constants.CLASS_TYPE.indexOf(selectedClass.class_type))
    }
}