package donaciones.service.impl;

import donaciones.model.VoluntarioCampania;
import donaciones.repository.VoluntarioCampaniaRepository;
import donaciones.service.IVoluntarioCampaniaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VoluntarioCampaniaServiceImpl implements IVoluntarioCampaniaService {

    @Autowired
    private VoluntarioCampaniaRepository voluntarioCampaniaRepository;

    @Override
    @Transactional
    public VoluntarioCampania registrarVoluntarioEnCampania(VoluntarioCampania voluntarioCampania) {
        return voluntarioCampaniaRepository.save(voluntarioCampania);
    }

    @Override
    @Transactional
    public void eliminarVoluntarioDeCampania(Long id) {
        voluntarioCampaniaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoluntarioCampania> listarVoluntariosPorCampania(Long campaniaId) {
        return voluntarioCampaniaRepository.findByCampaniaId(campaniaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoluntarioCampania> listarCampaniasPorVoluntario(Long usuarioId) {
        return voluntarioCampaniaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean esVoluntarioEnCampania(Long usuarioId, Long campaniaId) {
        return voluntarioCampaniaRepository.existsByUsuarioIdAndCampaniaId(usuarioId, campaniaId);
    }

    @Override
    @Transactional
    public VoluntarioCampania actualizarVoluntarioEnCampania(VoluntarioCampania voluntarioCampania) {
        if (!voluntarioCampaniaRepository.existsById(voluntarioCampania.getId())) {
            throw new IllegalArgumentException("El voluntario no existe");
        }
        return voluntarioCampaniaRepository.save(voluntarioCampania);
    }

    @Override
    @Transactional(readOnly = true)
    public VoluntarioCampania obtenerPorId(Long id) {
        return voluntarioCampaniaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Voluntario no encontrado con ID: " + id));
    }
}