package com.doctores.doctores.services

import com.doctores.doctores.domains.entity.Appointment
import com.doctores.doctores.domains.entity.Doctor
import com.doctores.doctores.domains.request.CreateAppointmentRequest
import com.doctores.doctores.domains.responses.CreateAppointmentResponse
import com.doctores.doctores.repositories.AppointmentRepository
import com.doctores.doctores.repositories.DoctorRepository
import com.doctores.doctores.repositories.PatientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class AppointmentService {
    @Autowired
    private lateinit var appointmentRepository: AppointmentRepository

    @Autowired
    private lateinit var patientRepository: PatientRepository

    fun createAppointment(request: CreateAppointmentRequest): CreateAppointmentResponse{

        val especialidad = appointmentRepository.getDoctorEspecialidad(request.idDoctor)

        if (especialidad.equals(request.especialidad)){
            print(request.idPaciente)
            val appointment = appointmentRepository.save(
                Appointment(
                    horario = request.horario,
                    especialidad = request.especialidad,
                    doc = request.idDoctor,
                    idPac = request.idPaciente,
                )
            )
            return CreateAppointmentResponse(
                idPaciente = request.idPaciente,
                especialidad = request.especialidad,
                doctor = "Carlos",
                consultorio = 101,
                horario = request.horario
            )
        }
        return CreateAppointmentResponse(
            idPaciente = "1",
            especialidad = request.especialidad,
            doctor = "Carlos",
            consultorio = 101,
            horario = "16"
        )
    }

    fun getAllAppointments(): List<Appointment>{
        var response = appointmentRepository.findAll()
        return response
    }

    fun getAppointmentById(id: Long): List<Appointment>{
        var citas = appointmentRepository.getAppointmentById(id)
        return citas
    }

    fun updateAppointment(id: Long): CreateAppointmentResponse{
        return CreateAppointmentResponse(
            idPaciente = "1",
            especialidad = "Test",
            doctor = "Carlos",
            consultorio = 101,
            horario = "16"
        )
    }

    fun deleteAppointment(id: Long): Unit{
        var delete = appointmentRepository.deleteAppointmentByById(id)
    }
}