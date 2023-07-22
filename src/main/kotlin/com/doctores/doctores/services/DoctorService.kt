package com.doctores.doctores.services

import com.doctores.doctores.domains.request.CreateDoctorRequest
import com.doctores.doctores.domains.responses.DoctorResponse
import com.doctores.doctores.repositories.DoctorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import com.doctores.doctores.domains.entity.Doctor
import org.springframework.dao.DataAccessException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import utils.Especialidades
import java.util.*

@Service
class DoctorService {
    @Autowired
    private lateinit var doctorRepository: DoctorRepository

    fun createDoctor(request: CreateDoctorRequest): DoctorResponse {

        Especialidades.values().find { it.especialidad == request.especialidad }
            ?: throw Error("Especialidad not found")

        val doctor = doctorRepository.save(
            Doctor(
                nombre = request.nombre,
                apellido = request.apellido,
                especialidad = request.especialidad,
                correo = request.correo,
                consultorio = request.consultorio,
            )
        )

        return DoctorResponse(
            message = "Doctor created successfully",
            doctor = doctor
        )
    }
    fun getAllDoctors(): List<Doctor> = doctorRepository.findAll()

    fun getDoctorById(id: Long): DoctorResponse {
        val doctor = doctorRepository.findByIdOrNull(id);
        if (doctor !== null){
            return DoctorResponse(message = "Doctor found", doctor = doctor)
        }
        return DoctorResponse(message = "Doctor does not exist")
    }

    fun updateDoctor(id: Long, request: CreateDoctorRequest): DoctorResponse {
        Especialidades.values().find { it.especialidad == request.especialidad }
            ?: throw Error("Especialidad not found")
        try {
            val doctor = doctorRepository.findByIdOrNull(id) ?: throw Error("Doctor does not exist");
            doctor.especialidad = request.especialidad;
            val updateDoctor = doctorRepository.save(doctor)
            return DoctorResponse("Doctor update", updateDoctor)
        } catch (e: Error){
            throw Error(e.message)
        }
    }

    fun deleteDoctor(id: Long): DoctorResponse {
        try{
            val doctor = doctorRepository.findByIdOrNull(id) ?: throw Error("Doctor does not exist");
            doctorRepository.deleteById(id)
            return DoctorResponse("El registro se ha borrado con exito", doctor)
        } catch (e: Error) {
            return DoctorResponse(e.message)
        }
    }

}