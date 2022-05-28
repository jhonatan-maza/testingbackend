package com.proyecto.web.controller;

import com.proyecto.web.model.Perfil;
import com.proyecto.web.security.JwtTokenUtil;
import com.proyecto.web.service.PerfilService;
import com.proyecto.web.util.JsonRespuestaCant;
import com.proyecto.web.util.RespuestaTransaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/app/perfil")
public class PerfilController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PerfilService perfilService;

    @GetMapping("/listar/{idnivel}/{estado}/{start}/{length}/{orderby}")
    public ResponseEntity<List<Perfil>> ListarPerfiles(@PathVariable(value = "idnivel") Integer idNivel,
                                                       @PathVariable(value = "estado") String estado,
                                                       @PathVariable(value = "start") String pagStart,
                                                       @PathVariable(value = "length") String pagLength,
                                                       @PathVariable(value = "orderby") String orderBy, HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(username != null){
            try{
                List<Perfil> lista = perfilService.ListarPerfil(idNivel,null,estado,pagStart,pagLength,orderBy);
                return new ResponseEntity<>(lista, HttpStatus.OK);
            }catch (Exception e){
                return null;
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cantidad/{idnivel}/{estado}")
    public ResponseEntity<JsonRespuestaCant> CantidadPerfil(@PathVariable(value = "idnivel") Integer idNivel,
                                                            @PathVariable(value = "estado") String estado, HttpServletRequest request) {
        JsonRespuestaCant respuesta = new JsonRespuestaCant();
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(username != null){
            try{
                Integer cantidad = perfilService.CantidadPerfil(idNivel,null,estado);
                respuesta.setCantidad(cantidad);
                return new ResponseEntity<>(respuesta, HttpStatus.OK);
            }catch (Exception e){
                return null;
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/view/{idperfil}")
    public ResponseEntity<Perfil> viewPerfil(@PathVariable(value = "idperfil") Integer idperfil, HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(username != null){
            try{
                Perfil view = perfilService.viewPerfil(idperfil);
                return new ResponseEntity<>(view, HttpStatus.OK);
            }catch (Exception e){
                return null;
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<RespuestaTransaccion> mantenimientoSavePerfil(@RequestBody Perfil perfil, HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(username != null){
            try{
                RespuestaTransaccion rpt = perfilService.mantenimientoPerfil(perfil,username,1);
                return new ResponseEntity<>(rpt, HttpStatus.OK);
            }catch (Exception e){
                return null;
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{operacion}")
    public ResponseEntity<RespuestaTransaccion> mantenimientoUpdatePerfil(@PathVariable(value = "operacion") Integer operacion,
                                                                         @RequestBody Perfil perfil, HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(username != null){
            try{
                RespuestaTransaccion rpt = perfilService.mantenimientoPerfil(perfil,username,operacion);
                return new ResponseEntity<>(rpt, HttpStatus.OK);
            }catch (Exception e){
                return null;
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
