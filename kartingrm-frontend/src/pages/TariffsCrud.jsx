// src/pages/TariffsCrud.jsx
import { useEffect, useState } from 'react'
import {
  Paper, Typography, Table, TableHead, TableBody, TableRow, TableCell,
  TextField, IconButton, CircularProgress, Stack
} from '@mui/material'
import SaveIcon   from '@mui/icons-material/Save'
import tariffSvc  from '../services/tariff.service'

export default function TariffsCrud () {
  const [rows, setRows]    = useState([])
  const [saving, setSaving]= useState(false)

  const load = () => tariffSvc.list().then(setRows)

  useEffect(load, [])

  const handleChange = (idx, field, value) => {
    setRows(r => {
      const clone = [...r]
      clone[idx] = { ...clone[idx], [field]: value }
      return clone
    })
  }

  const save = async (row) => {
    setSaving(true)
    try {
      await tariffSvc.update(row.rate, { price:+row.price, minutes:+row.minutes })
      alert('Tarifa actualizada')
      load()
    } catch (e) {
      alert(e.response?.data?.message ?? e.message)
    } finally {
      setSaving(false)
    }
  }

  return (
    <Paper sx={{ p:3 }}>
      <Stack direction="row" justifyContent="space-between" mb={2}>
        <Typography variant="h6">Tarifas / Precios</Typography>
        {saving && <CircularProgress size={20}/>}
      </Stack>

      <Table size="small">
        <TableHead>
          <TableRow>
            {['Tarifa','Precio (CL$)','Min.',''].map(h=>
              <TableCell key={h}>{h}</TableCell>)}
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((r,i)=>(
            <TableRow key={r.rate}>
              <TableCell>{r.rate}</TableCell>
              <TableCell>
                <TextField variant="standard" type="number" size="small"
                  value={r.price} onChange={e=>handleChange(i,'price',e.target.value)}/>
              </TableCell>
              <TableCell>
                <TextField variant="standard" type="number" size="small"
                  value={r.minutes} onChange={e=>handleChange(i,'minutes',e.target.value)}/>
              </TableCell>
              <TableCell>
                <IconButton onClick={()=>save(r)} disabled={saving}>
                  <SaveIcon/>
                </IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Paper>
  )
}
