package com.doctores.doctores.controllers

import com.doctores.doctores.constants.*
import com.doctores.doctores.domains.entity.Doctor
import com.doctores.doctores.domains.request.CreateDoctorRequest
import com.doctores.doctores.domains.responses.DoctorResponse
import com.doctores.doctores.domains.responses.HealthCheckResponse
import com.doctores.doctores.services.DoctorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import  org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.*


@RestController
class DoctorController {
    @Autowired
    private lateinit var doctorService: DoctorService

    @GetMapping(Doctor)
    fun getAllDoctors(): List<Doctor> = doctorService.getAllDoctors()

    @PostMapping(CreateDoctors)
    fun createDoctor(@RequestBody @Validated request: CreateDoctorRequest): ResponseEntity<DoctorResponse> {
        try {
            return ResponseEntity(doctorService.createDoctor(request), HttpStatus.CREATED)
        } catch (e: Error) {
            return ResponseEntity(DoctorResponse(message=e.message), HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping(GetDoctorById)
    fun getDoctorById(
            @PathVariable("id") id: Long
    ): DoctorResponse = doctorService.getDoctorById(id)

    @PutMapping(UpdateDoctor)
    fun updateDoctor(
        @PathVariable("id") id: Long,
        @RequestBody @Validated request: CreateDoctorRequest
    ): ResponseEntity<DoctorResponse> {
        try{
            return ResponseEntity(doctorService.updateDoctor(id, request), HttpStatus.ACCEPTED)
        }catch (e: Error){
            return ResponseEntity(DoctorResponse(message=e.message), HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping(DeleteDoctor)
    fun deleteDoctor(
            @PathVariable("id") id: Long
    ): ResponseEntity<DoctorResponse> {
        try{
            return ResponseEntity(doctorService.deleteDoctor(id), HttpStatus.ACCEPTED)
        }catch (e: Error){
            return ResponseEntity(DoctorResponse(message=e.message), HttpStatus.BAD_REQUEST)
        }
    }
}