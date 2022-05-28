package com.proyecto.web.controller;

import com.proyecto.web.model.Operacion;
import com.proyecto.web.security.JwtTokenUtil;
import com.proyecto.web.service.OperacionService;
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
@RequestMapping("/app/operacion")
public class OperacionController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private OperacionService operacionService;

    @GetMapping("/listar/{estado}/{start}/{length}/{orderby}")
    public ResponseEntity<List<Operacion>> ListarOperacion(@PathVariable(value = "estado") String estado,
                                                           @PathVariable(value = "start") String pagStart,
                                                           @PathVariable(value = "length") String pagLength,
                                                           @PathVariable(value = "orderby") String orderBy, HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(username != null){
            try{
                List<Operacion> lista = operacionService.ListarOperacion(null,estado,pagStart,pagLength,orderBy);
                return new ResponseEntity<>(lista, HttpStatus.OK);
            }catch (Exception e){
                return null;
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cantidad/{estado}")
    public ResponseEntity<JsonRespuestaCant> CantidadOperacion(@PathVariable(value = "estado") String estado, HttpServletRequest request) {
        JsonRespuestaCant respuesta = new JsonRespuestaCant();
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(username != null){
            try{
                Integer cantidad = operacionService.CantidadOperacion(null,estado);
                respuesta.setCantidad(cantidad);
                return new ResponseEntity<>(respuesta, HttpStatus.OK);
            }catch (Exception e){
                return null;
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/view/{idoperacion}")
    public ResponseEntity<Operacion> viewOperacion(@PathVariable(value = "idoperacion") Integer idoperacion, HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(username != null){
            try{
                Operacion view = operacionService.viewOperacion(idoperacion);
                return new ResponseEntity<>(view, HttpStatus.OK);
            }catch (Exception e){
                return null;
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<RespuestaTransaccion> mantenimientoSaveOperacion(@RequestBody Operacion operacion, HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(username != null){
            try{
                RespuestaTransaccion rpt = operacionService.mantenimientoOperacion(operacion,username,1);
                return new ResponseEntity<>(rpt, HttpStatus.OK);
            }catch (Exception e){
                return null;
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{nrooperacion}")
    public ResponseEntity<RespuestaTransaccion> mantenimientoUpdateOperacion(@PathVariable(value = "nrooperacion") Integer nroOperacion,
                                                                         @RequestBody Operacion operacion, HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(username != null){
            try{
                RespuestaTransaccion rpt = operacionService.mantenimientoOperacion(operacion,username,nroOperacion);
                return new ResponseEntity<>(rpt, HttpStatus.OK);
            }catch (Exception e){
                return null;
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
