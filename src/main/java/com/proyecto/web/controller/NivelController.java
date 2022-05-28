package com.proyecto.web.controller;

import com.proyecto.web.model.Nivel;
import com.proyecto.web.security.JwtTokenUtil;
import com.proyecto.web.service.NivelService;
import com.proyecto.web.util.JsonRespuestaCant;
import com.proyecto.web.util.Metodos;
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
@RequestMapping("/app/nivel")
public class NivelController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private NivelService nivelService;

    @GetMapping("/listar/{estado}/{start}/{length}/{orderby}")
    public ResponseEntity<List<Nivel>> ListarNiveles(@PathVariable(value = "estado") String estado,
                                                     @PathVariable(value = "start") String pagStart,
                                                     @PathVariable(value = "length") String pagLength,
                                                     @PathVariable(value = "orderby") String orderBy, HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(username != null){
            try{
//                List<Nivel> lista = nivelService.ListarNivel(descripcion,estado,pagStart,pagLength,orderBy);
//                List<Nivel> lista = nivelService.ListarNivel(descripcion.equals("null")?null:descripcion,estado,pagStart,pagLength,orderBy);
                List<Nivel> lista = nivelService.ListarNivel(null,estado,pagStart,pagLength,orderBy);
                return new ResponseEntity<>(lista, HttpStatus.OK);
            }catch (Exception e){
                return null;
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cantidad/{estado}")
    public ResponseEntity<JsonRespuestaCant> CantidadNivel(@PathVariable(value = "estado") String estado, HttpServletRequest request) {
        JsonRespuestaCant respuesta = new JsonRespuestaCant();
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(username != null){
            try{
                Integer cantidad = nivelService.CantidadNivel(null,estado);
                respuesta.setCantidad(cantidad);
                return new ResponseEntity<>(respuesta, HttpStatus.OK);
            }catch (Exception e){
                return null;
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/view/{idnivel}")
    public ResponseEntity<Nivel> viewNivel(@PathVariable(value = "idnivel") Integer idnivel, HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(username != null){
            try{
                Nivel view = nivelService.viewNivel(idnivel);
                return new ResponseEntity<>(view, HttpStatus.OK);
            }catch (Exception e){
                return null;
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<RespuestaTransaccion> mantenimientoSaveNivel(@RequestBody Nivel nivel, HttpServletRequest request) {
        RespuestaTransaccion respTransaccion = new RespuestaTransaccion();
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(username != null){
            try{
                RespuestaTransaccion rpt = nivelService.mantenimientoNivel(nivel,username,1);
                return new ResponseEntity<>(rpt, HttpStatus.OK);
            }catch (Exception e){
                return null;
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{operacion}")
    public ResponseEntity<RespuestaTransaccion> mantenimientoUpdateNivel(@PathVariable(value = "operacion") Integer operacion,
                                                                         @RequestBody Nivel nivel, HttpServletRequest request) {
        RespuestaTransaccion respTransaccion = new RespuestaTransaccion();
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(username != null){
            try{
                RespuestaTransaccion rpt = nivelService.mantenimientoNivel(nivel,username,operacion);
                return new ResponseEntity<>(rpt, HttpStatus.OK);
            }catch (Exception e){
                return null;
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
