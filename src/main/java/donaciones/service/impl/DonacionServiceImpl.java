package donaciones.service.impl;

import donaciones.dto.request.DonacionRequest;
import donaciones.dto.response.DonacionResponse;
import donaciones.exception.RecursoNoEncontradoException;
import donaciones.model.*;
import donaciones.model.enums.DonacionEstado;
import donaciones.repository.*;
import donaciones.service.DonacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonacionServiceImpl implements DonacionService {

    private final DonacionRepository donacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final CampaniaRepository campaniaRepository;
    private final OrganizacionRepository organizacionRepository;

    @Override
    @Transactional
    public DonacionResponse crearDonacion(DonacionRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        
        Campania campania = campaniaRepository.findById(request.getCampaniaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Campaña no encontrada"));
        
        Organizacion organizacion = organizacionRepository.findById(request.getOrganizacionId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Organización no encontrada"));

        Donacion donacion = new Donacion();
        donacion.setTipo(request.getTipo());
        donacion.setMonto(request.getMonto());
        donacion.setItems(request.getItems());
        donacion.setUsuario(usuario);
        donacion.setCampania(campania);
        donacion.setOrganizacion(organizacion);
        donacion.setEstado(DonacionEstado.PENDIENTE);

        Donacion savedDonacion = donacionRepository.save(donacion);
        return mapToDonacionResponse(savedDonacion);
    }

    @Override
    public DonacionResponse actualizarEstado(Long id, String estado) {
        Donacion donacion = donacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Donación no encontrada"));
        
        donacion.setEstado(DonacionEstado.valueOf(estado.toUpperCase()));
        Donacion updatedDonacion = donacionRepository.save(donacion);
        return mapToDonacionResponse(updatedDonacion);
    }

    @Override
    public List<DonacionResponse> listarDonacionesPorUsuario(Long usuarioId) {
        return donacionRepository.findByUsuarioId(usuarioId).stream()
                .map(this::mapToDonacionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonacionResponse> listarDonacionesPorCampania(Long campaniaId) {
        return donacionRepository.findByCampaniaId(campaniaId).stream()
                .map(this::mapToDonacionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DonacionResponse obtenerDonacionPorId(Long id) {
        Donacion donacion = donacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Donación no encontrada"));
        return mapToDonacionResponse(donacion);
    }

    private DonacionResponse mapToDonacionResponse(Donacion donacion) {
        DonacionResponse response = new DonacionResponse();
        response.setId(donacion.getId());
        response.setTipo(donacion.getTipo());
        response.setMonto(donacion.getMonto());
        response.setItems(donacion.getItems());
        response.setUsuarioId(donacion.getUsuario().getId());
        response.setCampaniaId(donacion.getCampania().getId());
        response.setOrganizacionId(donacion.getOrganizacion().getId());
        response.setEstado(donacion.getEstado());
        response.setIsAnonima(donacion.getIsAnonima());
        response.setCreatedAt(donacion.getCreatedAt());
        return response;
    }
    @Override
    public List<DonacionResponse> listarDonacionesPorOrganizacion(Long organizacionId, String estado) {
        List<Donacion> donaciones;
        if (estado == null || estado.isEmpty()) {
            donaciones = donacionRepository.findByOrganizacionId(organizacionId);
        } else {
            DonacionEstado estadoEnum = DonacionEstado.valueOf(estado.toUpperCase());
            donaciones = donacionRepository.findByOrganizacionIdAndEstado(organizacionId, estadoEnum);
        }
        return donaciones.stream()
                .map(this::mapToDonacionResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DonacionResponse editarDonacionPorId(Long id, Long organizacionId, DonacionRequest request) {
        Donacion donacion = donacionRepository.findByIdAndOrganizacionId(id, organizacionId);
        if (donacion == null) {
            throw new RecursoNoEncontradoException("Donación no encontrada");
        }

        donacion.setTipo(request.getTipo());
        donacion.setMonto(request.getMonto());
        donacion.setItems(request.getItems());

        Donacion updatedDonacion = donacionRepository.save(donacion);
        return mapToDonacionResponse(updatedDonacion);
    }

    @Override
    @Transactional
    public List<DonacionResponse> asignarCampaniaADonacion(Long campaniaId, Long organizacionId) {
        List<Donacion> donaciones = donacionRepository.findByCampaniaIdAndOrganizacionId(campaniaId, organizacionId);
        return donaciones.stream()
                .map(this::mapToDonacionResponse)
                .collect(Collectors.toList());
    }
    // eliminar donacion por id
    @Override
    public void eliminarDonacionPorId(Long id) {
        donacionRepository.deleteById(id);
    }
    @Override
    // listar todas las donaciones
    public List<DonacionResponse> listarTodasLasDonaciones() {
        return donacionRepository.findAll().stream()
                .map(this::mapToDonacionResponse)
                .collect(Collectors.toList());
    }
    
}